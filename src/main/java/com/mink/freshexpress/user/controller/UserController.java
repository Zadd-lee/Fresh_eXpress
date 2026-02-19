package com.mink.freshexpress.user.controller;

import com.mink.freshexpress.user.dto.UserResponseDto;
import com.mink.freshexpress.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

}
