package com.mashinakomplekt.mysystem.controllers;

import com.mashinakomplekt.mysystem.dto.InfoResponse.AppResponseDto;
import com.mashinakomplekt.mysystem.services.AuthService;
import com.mashinakomplekt.mysystem.dto.JwtDto.JwtRequest;
import com.mashinakomplekt.mysystem.dto.JwtDto.JwtResponse;
import com.mashinakomplekt.mysystem.dto.UserDto.RegistartionUserRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        log.info("Request body: {}", authRequest);
        Optional<String> token = authService.createAuthToken(authRequest);
        if (token.isPresent()) {
            return new ResponseEntity<JwtResponse>(new JwtResponse(token.get()), HttpStatus.OK);
        }
        return new ResponseEntity<AppResponseDto>(new AppResponseDto(HttpStatus.BAD_REQUEST.value(), "Пользователь не найден"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createNewUser(@RequestBody @Valid RegistartionUserRequest userRequest) {
        log.info("Request body: {}", userRequest);
        AppResponseDto appError = authService.createNewUser(userRequest);
        return new ResponseEntity<AppResponseDto>(appError, HttpStatus.valueOf(appError.getStatus()));
    }
}
