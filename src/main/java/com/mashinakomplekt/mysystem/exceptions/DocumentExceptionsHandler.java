package com.mashinakomplekt.mysystem.exceptions;


import com.mashinakomplekt.mysystem.dto.InfoResponse.AppResponseDto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.InvalidParameterException;


@RestControllerAdvice
@Slf4j
public class DocumentExceptionsHandler {

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<AppResponseDto> DocumentInvalidExceptionsHandler(InvalidParameterException ex) {
        log.error(ex.getMessage());
        AppResponseDto errView = new AppResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
        return new ResponseEntity<AppResponseDto>(errView, HttpStatus.BAD_REQUEST);
    }
}
