package com.mashinakomplekt.mysystem.dto.UserDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class RegistartionUserRequest {

    @Min(5)
    @Max(50)
    private String username;

    @Min(8)
    @Max(100)
    private String password;

    private String confirmPassword;

    @Email
    private String email;

    @Min(2)
    @Max(50)
    private String name;

}
