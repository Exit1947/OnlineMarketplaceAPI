package com.onlineShop.dto.auth;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Email is required option")
    @Length(min = 3, max = 50, message = "Email number must be between 10 and 20 characters")
    public String email;

    @NotBlank(message = "Password is required option")
    @Column(name = "password")
    public String password;

}
