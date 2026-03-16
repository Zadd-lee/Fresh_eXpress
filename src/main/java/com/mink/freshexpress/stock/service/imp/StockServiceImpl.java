package com.mink.freshexpress.stock.service.imp;

import com.mink.freshexpress.common.exception.CustomException;
import com.mink.freshexpress.common.exception.constant.*;
import com.mink.freshexpress.order.model.Order;
import com.mink.freshexpress.order.model.StockReservation;
import com.mink.freshexpress.order.repository.OrderRepository;
import com.mink.freshexpress.product.model.Product;
import com.mink.freshexpress.product.repository.ProductRepository;
import com.mink.freshexpress.stock.constant.Status;
import com.mink.freshexpress.stock.dto.CreateStockRequestDto;
import com.mink.freshexpress.stock.dto.CreateStockReservationDto;
import com.mink.freshexpress.stock.dto.StockResponseDto;
import com.mink.freshexpress.stock.model.Stock;
import com.mink.freshexpress.stock.repository.StockRepository;
import com.mink.freshexpress.stock.repository.StockReservationRepository;
import com.mink.freshexpress.stock.service.StockService;
import com.mink.freshexpress.warehouse.model.WarehouseLocation;
import com.mink.freshexpress.warehouse.repository.WarehouseLocationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

import static com.mink.freshexpress.common.util.DateFormatter.getDateTime;
import static com.mink.freshexpress.common.util.Validator.valid;


@RequiredArgsConstructor
@Service
public class StockServiceImpl implements StockService {
    private final WarehouseLocationRepository warehouseLocationRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;
    private final StockReservationRepository stockReservationRepository;


    @Transactional
    @Override
    public void create(CreateStockRequestDto dto) {

        //valid
        WarehouseLocation location = valid(warehouseLocationRepository.findById(dto.getLocationId()), WarehouseErrorCode.NOT_FOUND_LOCATION);
        Product product = valid(productRepository.findById(dto.getProductId()), ProductErrorCode.NOT_FOUND);

        //product의 보관 온도와 다른 보관온도를 가진 location을 설정시 error 출력
        validateTemp(product, location);

        //stock 객체 생성
        Stock stock = createStock(dto, product);
        stock.updateLocation(location);
        stock.updateProduct(product);


        stockRepository.save(stock);


    }

    private static Stock createStock(CreateStockRequestDto dto, Product product) {
        Stock stock = dto.toEntity();

        LocalDate now = LocalDate.now();
        getManufacturedAt(dto, stock);
        //유통기한이 기입되지 않았을때 product의 기본 유통 기한을 자동으로 기입
        stock.updateExpiredAt(getExpiredAt(dto, product));
        //stock의 유통기한 기준으로 status 설정
        setStatus(stock, now);
        return stock;
    }

    @Transactional
    @Override
    public void creatBulk(List<CreateStockRequestDto> dtoList) {
        Map<Long, Product> productMap = new HashMap<>();
        Map<Long, WarehouseLocation> locationMap = new HashMap<>();

        List<Stock> stockList = new ArrayList<>();

        for (CreateStockRequestDto dto : dtoList) {
            //valid
            Product product = getProduct(dto, productMap);
            WarehouseLocation location = getLocation(dto, locationMap);

            //product의 보관 온도와 다른 보관온도를 가진 location을 설정시 error 출력
            validateTemp(product, location);

            //stock 객체 생성
            Stock stock = createStock(dto, product);
            stock.updateLocation(location);
            stock.updateProduct(product);

            stockList.add(stock);
        }

        stockRepository.saveAll(stockList);
    }

    @Override
    public StockResponseDto get(long id) {
        return new StockResponseDto(valid(stockRepository.findById(id), StockErrorCode.NOT_FOUND));
    }

    @Transactional
    @Override
    public void discard(Long id) {
        Stock stock = valid(stockRepository.findById(id), StockErrorCode.NOT_FOUND);
        if (stock.getStatus() == Status.DISCARDED) {
            throw new CustomException(StockErrorCode.ALREADY_DISCARD);
        } else {
            stock.updateStatus(Status.DISCARDED);
        }
    }

    @Transactional
    @Override
    public void createReservation(List<CreateStockReservationDto> dtoList) {
        List<StockReservation> stockReservationList = new ArrayList<>();
        for (CreateStockReservationDto dto : dtoList) {
            //valid
            Order order = valid(orderRepository.findById(dto.getOrderId()), CommonErrorCode.INTERNAL_SERVER_ERROR);
            Stock stock = valid(stockRepository.findById(dto.getStockId()), CommonErrorCode.INTERNAL_SERVER_ERROR);

            StockReservation stockReservation = dto.toEntity();
            stockReservation.updateOrder(order);
            stockReservation.updateStock(stock);

            stockReservationList.add(stockReservation);
        }
        stockReservationRepository.saveAll(stockReservationList);

    }

    private Product getProduct(CreateStockRequestDto dto, Map<Long, Product> productMap) {
        Product product;
        if (!productMap.containsKey(dto.getProductId())) {
            product = valid(productRepository.findById(dto.getProductId()), ProductErrorCode.NOT_FOUND);
            productMap.put(dto.getProductId(), product);
        }else {
            product = productMap.get(dto.getProductId());
        }
        return product;
    }

    private WarehouseLocation getLocation(CreateStockRequestDto dto, Map<Long, WarehouseLocation> locationMap) {
        WarehouseLocation location;
        if (!locationMap.containsKey(dto.getLocationId())) {
            location = valid(warehouseLocationRepository.findById(dto.getLocationId()), WarehouseErrorCode.NOT_FOUND_LOCATION);
            locationMap.put(dto.getLocationId(), location);
        } else {
            location = locationMap.get(dto.getLocationId());
        }
        return location;
    }

    private static void validateTemp(Product product, WarehouseLocation location) {
        if (!product.getStorageTemp().equals(location.getTemperature())) {
            throw new CustomException(StockErrorCode.STORAGE_TEMP_MISMATCH);
        }
    }

    private static LocalDateTime getExpiredAt(CreateStockRequestDto dto, Product product) {

        if (dto.getExpiredAt() != null) {
            return getDateTime(dto.getExpiredAt());
        } else {
            return LocalDate.now().plusDays(product.getDefaultShelfLifeDays()).atStartOfDay();
        }
    }



    private static void getManufacturedAt(CreateStockRequestDto dto, Stock stock) {
        if (dto.getManufacturedAt() != null) {
            LocalDate manufacturedAt = getDateTime(dto.getManufacturedAt()).toLocalDate();
            stock.updateManufacturedAt(manufacturedAt);
        }
    }

    private static void setStatus(Stock stock, LocalDate now) {
        int days = Period.between(LocalDate.from(stock.getExpiredAt()), now).getDays();
        if (days <= 0) stock.updateStatus(Status.EXPIRED);
        else if (days <= 3) stock.updateStatus(Status.EXPIRING_SOON);
        else stock.updateStatus(Status.NORMAL);
    }
}
