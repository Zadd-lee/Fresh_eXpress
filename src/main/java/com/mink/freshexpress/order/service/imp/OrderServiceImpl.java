package com.mink.freshexpress.order.service.imp;

import com.mink.freshexpress.common.exception.CustomException;
import com.mink.freshexpress.common.exception.constant.CommonErrorCode;
import com.mink.freshexpress.common.exception.constant.StockErrorCode;
import com.mink.freshexpress.common.exception.constant.WarehouseErrorCode;
import com.mink.freshexpress.order.dto.CreateOrderItemRequestDto;
import com.mink.freshexpress.order.dto.CreateOrderRequestDto;
import com.mink.freshexpress.order.model.Order;
import com.mink.freshexpress.order.model.OrderItem;
import com.mink.freshexpress.order.repository.OrderItemRepository;
import com.mink.freshexpress.order.repository.OrderRepository;
import com.mink.freshexpress.order.service.OrderService;
import com.mink.freshexpress.product.model.Product;
import com.mink.freshexpress.stock.model.Stock;
import com.mink.freshexpress.user.model.User;
import com.mink.freshexpress.user.repository.UserRepository;
import com.mink.freshexpress.warehouse.model.Warehouse;
import com.mink.freshexpress.warehouse.repository.WarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @Transactional
    @Override
    public void create(String username, CreateOrderRequestDto dto) {
        //valid
        User user = valid(userRepository.findByEmail(username), CommonErrorCode.INTERNAL_SERVER_ERROR);
        Warehouse warehouse = valid(warehouseRepository.findById(dto.getWarehouseId()), WarehouseErrorCode.NOT_FOUND_WAREHOUSE);

        Map<Long, Stock> stockMap = warehouse.getAllStockList()
                .stream()
                .collect(Collectors.toMap(Stock::getId, Function.identity()));

        Order order = dto.toEntity();
        order.updateCustomer(user);

        List<OrderItem> orderItemList = new ArrayList<>();

        for (CreateOrderItemRequestDto orderItemRequestDto : dto.getOrderItemList()) {
            Stock stock = stockMap.get(orderItemRequestDto.getProductId());
            //valid stock 의 갯수와 order의 갯수가 맞는지 확인
            validOrderItemWithStock(orderItemRequestDto, stock);


            Product product = stock.getProduct();

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .quantity(BigDecimal.valueOf(orderItemRequestDto.getQuantity()))
                    .unit(product.getUnit())
                    .build();
            orderItemList.add(orderItem);
        }


        orderRepository.save(order);
        orderItemRepository.saveAll(orderItemList);
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
