package com.onlineShop.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Email is required option")
    @Length(min = 3, max = 50, message = "Email number must be between 10 and 20 characters")
    private String email;

    @Length(min = 3, max = 50, message = "Login number must be between 10 and 20 characters")
    private String login;

    @NotBlank(message = "Firstname is required option")
    @Length(min = 3, max = 50, message = "Firstname must be between 3 and 50 characters")
    private String firstName;

    @NotBlank(message = "Lastname is required option")
    @Length(min = 3, max = 50, message = "Lastname must be between 3 and 50 characters")
    private String lastName;

    @NotBlank(message = "Phone number is required option")
    @Length(min = 3, max = 50, message = "Phone number must be between 10 and 20 characters")
    private String phoneNumber;

    @NotBlank(message = "Password field is required")
    @Column(name = "password")
    private String password;

    private String avatar;

    @JsonIgnore
    private Date creationDate = new Date();

}
