package com.mashinakomplekt.mysystem.dto.UserDto;

import lombok.Data;

@Data
public class RegistartionUserRequest {
    // Валидацию сделать
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String name;

}
