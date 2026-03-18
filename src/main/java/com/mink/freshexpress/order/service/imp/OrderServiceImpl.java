package com.mink.freshexpress.order.service.imp;

import com.mink.freshexpress.common.exception.CustomException;
import com.mink.freshexpress.common.exception.constant.CommonErrorCode;
import com.mink.freshexpress.common.exception.constant.OrderErrorCode;
import com.mink.freshexpress.common.exception.constant.StockErrorCode;
import com.mink.freshexpress.common.exception.constant.WarehouseErrorCode;
import com.mink.freshexpress.order.constant.OrderStatus;
import com.mink.freshexpress.order.constant.ReservationStatus;
import com.mink.freshexpress.order.dto.CreateOrderItemRequestDto;
import com.mink.freshexpress.order.dto.CreateOrderRequestDto;
import com.mink.freshexpress.order.dto.OrderResponseDto;
import com.mink.freshexpress.order.model.Order;
import com.mink.freshexpress.order.model.OrderItem;
import com.mink.freshexpress.order.model.StockReservation;
import com.mink.freshexpress.order.repository.OrderItemRepository;
import com.mink.freshexpress.order.repository.OrderRepository;
import com.mink.freshexpress.order.service.OrderService;
import com.mink.freshexpress.product.model.Product;
import com.mink.freshexpress.stock.constant.StockHistoryType;
import com.mink.freshexpress.stock.dto.CreateStockReservationDto;
import com.mink.freshexpress.stock.model.Stock;
import com.mink.freshexpress.stock.model.StockHistory;
import com.mink.freshexpress.stock.repository.StockHistoryRepository;
import com.mink.freshexpress.stock.repository.StockReservationRepository;
import com.mink.freshexpress.user.model.User;
import com.mink.freshexpress.user.repository.UserRepository;
import com.mink.freshexpress.warehouse.model.Warehouse;
import com.mink.freshexpress.warehouse.repository.WarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.mink.freshexpress.common.util.Validator.valid;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final WarehouseRepository warehouseRepository;
    private final StockHistoryRepository stockHistoryRepository;
    private final StockReservationRepository stockReservationRepository;

    @Transactional
    @Override
    public List<CreateStockReservationDto> create(String username, CreateOrderRequestDto dto) {
        //valid
        User user = valid(userRepository.findByEmail(username), CommonErrorCode.INTERNAL_SERVER_ERROR);
        Warehouse warehouse = valid(warehouseRepository.findById(dto.getWarehouseId()), WarehouseErrorCode.NOT_FOUND_WAREHOUSE);

        Map<Long, Stock> stockMapByProductId = warehouse.getAllStockList()
                .stream()
                .collect(Collectors.toMap(
                        stock -> stock.getProduct().getId(),
                        Function.identity()));

        //order 생성 및 저장
        Order order = dto.toEntity();
        order.updateCustomer(user);
        orderRepository.save(order);


        List<CreateStockReservationDto> stockReservationDtoList = new ArrayList<>();

        for (CreateOrderItemRequestDto orderItemRequestDto : dto.getOrderItemList()) {

            Stock stock = stockMapByProductId.get(orderItemRequestDto.getProductId());
            //valid stock 의 갯수와 order의 갯수가 맞는지 확인
            validOrderItemWithStock(orderItemRequestDto, stock);


            Product product = stock.getProduct();

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .stock(stock)
                    .product(stock.getProduct())
                    .quantity(BigDecimal.valueOf(orderItemRequestDto.getQuantity()))
                    .unit(product.getUnit())
                    .build();

            orderItemRepository.save(orderItem);

            //stock reservation Dto 생성
            stockReservationDtoList.add(new CreateStockReservationDto(orderItemRequestDto.getQuantity(), orderItem.getId(), stock.getId()));
        }


        return stockReservationDtoList;
    }

    @Override
    public OrderResponseDto findById(Long id) {
        return new OrderResponseDto(
                valid(orderRepository.findById(id), OrderErrorCode.NOT_FOUND)
        );

    }

    @Transactional
    @Override
    public void setShipped(String email, Long id) {
        //valid
        User warehouseManager = valid(userRepository.findByEmail(email), CommonErrorCode.INTERNAL_SERVER_ERROR);
        Order order = valid(orderRepository.findById(id), OrderErrorCode.NOT_FOUND);


        if (order.getStatus() == OrderStatus.PENDING) {
            throw new CustomException(OrderErrorCode.NOT_FOUND_RESERVATION);
        } else if (order.getStatus() != OrderStatus.PREPARING) {
            throw new CustomException(OrderErrorCode.ALREADY_SHIPPED);
        }

        //order status 변경
        order.updateStatus(OrderStatus.SHIPPED);

        //stock reservation 변경
        order.getStockReservationList()
                .forEach(stockReservation -> stockReservation.updateStatus(ReservationStatus.CONFIRMED));

        //stock history 생성
        List<StockHistory> stockHistoryList = new ArrayList<>();
        List<OrderItem> orderItemList = order.getOrderItemList();
        for (OrderItem orderItem : orderItemList) {
            Stock stock = orderItem.getStock();

            StockHistory stockHistory = StockHistory.builder()
                    .type(StockHistoryType.RELEASE)
                    .quantity(orderItem.getQuantity())
                    .actor(warehouseManager)
                    .order(order)
                    .stock(stock)
                    .build();

            stockHistoryList.add(stockHistory);
        }
        stockHistoryRepository.saveAll(stockHistoryList);


    }

    @Transactional
    @Override
    public void delete(String email, Long id) {
        //valid
        Order order = valid(orderRepository.findById(id), OrderErrorCode.NOT_FOUND);
        User user = valid(userRepository.findByEmail(email), CommonErrorCode.INTERNAL_SERVER_ERROR);


        if (order.getStatus() == OrderStatus.PENDING || order.getStatus() == OrderStatus.PREPARING) {
            // 상태 변경
            order.updateStatus(OrderStatus.CANCELLED);

            //예약 취소
            List<StockHistory> stockHistoryList = new ArrayList<>();


            List<StockReservation> stockReservationList = stockReservationRepository.findByOrder(order);
            for (StockReservation stockReservation : stockReservationList) {
                //예약 취소
                stockReservation.updateStatus(ReservationStatus.RELEASED);

                BigDecimal stockReservationQuantity = stockReservation.getQuantity();

                //stock 복구

                Stock stock = stockReservation.getStock();
                stock.updateCurrentQuantity(stock.getCurrentQuantity()
                        .add(stockReservationQuantity));

                // history 변경
                stockHistoryList.add(StockHistory.builder()
                        .quantity(stockReservationQuantity)
                        .order(order)
                        .stock(stock)
                        .type(StockHistoryType.RELEASE)
                        .actor(user)
                        .build());
            }

            stockHistoryRepository.saveAll(stockHistoryList);

        } else {
            order.updateStatus(OrderStatus.FAILED);
        }
    }


    private static void validOrderItemWithStock(CreateOrderItemRequestDto orderItemRequestDto, Stock stock) {
        if (stock == null) {
            throw new CustomException(StockErrorCode.NOT_FOUND);
        }

        if (stock.getCurrentQuantity().compareTo(BigDecimal.valueOf(orderItemRequestDto.getQuantity())) < 0) {
            throw new CustomException(StockErrorCode.OVERSTOCK_REQUEST);
        }
    }

}
