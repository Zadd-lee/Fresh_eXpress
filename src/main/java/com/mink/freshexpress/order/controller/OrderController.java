package com.mink.freshexpress.order.controller;

import com.mink.freshexpress.order.dto.CreateOrderRequestDto;
import com.mink.freshexpress.order.dto.OrderResponseDto;
import com.mink.freshexpress.order.service.OrderService;
import com.mink.freshexpress.stock.dto.CreateStockReservationDto;
import com.mink.freshexpress.stock.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        List<CreateStockReservationDto> stockReservationDto = orderService.create(username,dto);
        stockService.createReservation(stockReservationDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> find(@PathVariable Long id) {
        return new ResponseEntity<>(orderService.findById(id), HttpStatus.OK);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> setShipped(@AuthenticationPrincipal UserDetails authenticatedPrincipal,
                                           @PathVariable Long id) {
        orderService.setShipped(authenticatedPrincipal.getUsername(),id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
