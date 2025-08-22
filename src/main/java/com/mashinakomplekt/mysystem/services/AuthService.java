package com.mashinakomplekt.mysystem.services;

import com.mashinakomplekt.mysystem.dto.InfoResponse.AppResponseDto;
import com.mashinakomplekt.mysystem.models.User;
import com.mashinakomplekt.mysystem.utils.JwtTokenUtil;
import com.mashinakomplekt.mysystem.dto.JwtDto.JwtRequest;
import com.mashinakomplekt.mysystem.dto.UserDto.RegistartionUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    public Optional<String> createAuthToken(JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
            String token = jwtTokenUtil.generateToken(userDetails);
            return Optional.of(token);
        } catch (BadCredentialsException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    public AppResponseDto createNewUser(RegistartionUserRequest userRequest) {
        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            return new AppResponseDto(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают");
        }
        if (userService.findByUsername(userRequest.getUsername()).isPresent()) {
            return new AppResponseDto(HttpStatus.BAD_REQUEST.value(), "Пользователь с таким ником уже существует");
        }

        User user = userService.createNewUser(userRequest);
        return new AppResponseDto(HttpStatus.CREATED.value(), String.format("Пользователь %s создан", user.getUsername()));
    }
}
