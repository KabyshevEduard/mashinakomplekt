package com.mashinakomplekt.mysystem.dto.JwtDto;

import lombok.Data;

@Data
public class JwtRequest {
    // Валидацию сделать
    String username;
    String password;
}
