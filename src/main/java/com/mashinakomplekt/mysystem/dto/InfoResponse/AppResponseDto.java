package com.mashinakomplekt.mysystem.dto.InfoResponse;

import lombok.Data;

@Data
public class AppResponseDto {
    private int status;
    private String message;

    public AppResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
