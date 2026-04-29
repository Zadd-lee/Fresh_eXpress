package com.mink.freshexpress.delivery.controller;

import com.mink.freshexpress.delivery.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class DeliveryController {
    private final DeliveryService service;

    @PostMapping("/{orderId}")
    public ResponseEntity<Void> create(@AuthenticationPrincipal UserDetails authenticatedPrincipal,
                                       @PathVariable(name = "orderId") Long orderId) {
        service.create(authenticatedPrincipal.getUsername(),orderId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
