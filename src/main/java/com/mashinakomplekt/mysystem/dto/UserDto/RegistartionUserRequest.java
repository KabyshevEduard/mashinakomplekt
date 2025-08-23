package com.mashinakomplekt.mysystem.dto.UserDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RegistartionUserRequest {

    @Length(min = 5, max = 50)
    private String username;

    @Length(min = 8, max = 100)
    private String password;

    private String confirmPassword;

    @Email
    private String email;

    @Length(min = 2, max = 100)
    private String name;

}
