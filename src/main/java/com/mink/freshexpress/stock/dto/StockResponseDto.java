package com.mink.freshexpress.stock.dto;

import com.mink.freshexpress.product.model.Product;
import com.mink.freshexpress.stock.model.Stock;
import com.mink.freshexpress.warehouse.model.Warehouse;
import com.mink.freshexpress.warehouse.model.WarehouseLocation;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class StockResponseDto {

    private final String lot;
    private final String quantity;
    private final String manufacturedAt;
    private final String expiredAt;
    private final String status;

    private final String productId;
    private final String productName;
    private final String warehouseLocationId;
    private final String warehouseLocationCode;
    private final String warehouseId;
    private final String warehouseName;

    public StockResponseDto(Stock stock) {
        DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        WarehouseLocation warehouseLocation = stock.getLocation();
        Warehouse warehouse = warehouseLocation.getWarehouse();
        Product product = stock.getProduct();

        this.lot = stock.getLot();
        this.quantity = stock.getInitialQuantity().toString();
        this.manufacturedAt = stock.getManufacturedAt().format(dateformat);
        this.expiredAt = stock.getExpiredAt().format(dateformat);
        this.status = stock.getStatus().name();
        this.productId = product.getId().toString();
        this.productName = product.getName();
        this.warehouseLocationId = warehouseLocation.getId().toString();
        this.warehouseLocationCode = warehouseLocation.getCode();
        this.warehouseId = warehouse.getId().toString();
        this.warehouseName = warehouse.getName();
    }
}
