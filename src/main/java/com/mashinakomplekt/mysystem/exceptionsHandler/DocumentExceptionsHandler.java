package com.mashinakomplekt.mysystem.exceptionsHandler;


import com.mashinakomplekt.mysystem.dto.InfoResponse.AppResponseDto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
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

    @ExceptionHandler(IOException.class)
    public ResponseEntity<AppResponseDto> DocumentIOExceptionsHandler(IOException ex) {
        log.error(ex.getMessage());
        AppResponseDto errView = new AppResponseDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage()
        );
        return new ResponseEntity<AppResponseDto>(errView, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
