package com.mink.freshexpress.order.dto;

import com.mink.freshexpress.common.util.Generator;
import com.mink.freshexpress.order.constant.OrderStatus;
import com.mink.freshexpress.order.model.Order;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateOrderRequestDto {
    @NotBlank
    private String recipientName;
    private String recipientPhone;
    @NotBlank
    private String address;
    private String deliveryNote;

    @NotNull
    private List<CreateOrderItemRequestDto> orderItemList;

    @NotNull
    private Long warehouseId;
    public Order toEntity() {
        Order.OrderBuilder builder = Order.builder()
                .address(this.address)
                .orderNo(Generator.generateOrderNo())
                .recipientName(this.recipientName)
                .status(OrderStatus.PENDING);
        if(StringUtils.hasText(this.recipientPhone)) {
            builder.recipientPhone(this.recipientPhone);
        }

        if(StringUtils.hasText(this.deliveryNote)) {
            builder.deliveryNote(this.deliveryNote);
        }
        return builder.build();

    }
}
