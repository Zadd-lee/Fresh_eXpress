package com.mink.freshexpress.auth.controller;

import com.mink.freshexpress.auth.dto.JwtAuthResponseDto;
import com.mink.freshexpress.auth.dto.LoginRequestDto;
import com.mink.freshexpress.auth.dto.TokenRequestDto;
import com.mink.freshexpress.auth.service.UserService;
import com.mink.freshexpress.auth.utils.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("/signin")
    public ResponseEntity<Void> signIn(@Valid @RequestBody SigninRequestDto dto) {
        userService.signIn(dto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> login(@Valid @RequestBody LoginRequestDto dto) {
        return new ResponseEntity<>(userService.login(dto), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthResponseDto> refresh(@RequestBody TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(userService.refreshToken(tokenRequestDto), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        userService.logout(jwtProvider.resolveAccessToken(request));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
