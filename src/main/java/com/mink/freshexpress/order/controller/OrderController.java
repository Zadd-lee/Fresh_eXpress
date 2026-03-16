package com.mink.freshexpress.order.controller;

import com.mink.freshexpress.order.dto.CreateOrderRequestDto;
import com.mink.freshexpress.order.service.OrderService;
import com.mink.freshexpress.stock.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final StockService stockService;

    @PostMapping
    public ResponseEntity<Void> create(@AuthenticationPrincipal UserDetails authenticatedPrincipal
            , @Valid @RequestBody CreateOrderRequestDto dto) {
        String username = authenticatedPrincipal.getUsername();
        orderService.create(username,dto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
