package com.mink.freshexpress.user.controller;

import com.mink.freshexpress.user.dto.UserResponseDto;
import com.mink.freshexpress.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id, @AuthenticationPrincipal UserDetails authenticatedPrincipal) {
        service.deleteById(id, authenticatedPrincipal.getUsername());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
