package com.onlineShop.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class JwtProperties {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-days-of-token}")
    private int expirationDaysOfToken;

    @Value("${service.name}")
    private String serviceName;
}
