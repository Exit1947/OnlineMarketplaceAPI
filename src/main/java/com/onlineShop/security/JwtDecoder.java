package com.onlineShop.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtDecoder {

    private final JwtProperties prop;

    @Autowired
    public JwtDecoder(final JwtProperties prop) {
        this.prop = prop;
    }

    public DecodedJWT decode(final String token) {
        return JWT.require(Algorithm.HMAC256(prop.getSecret()))
                .withIssuer(prop.getServiceName())
                .build()
                .verify(token);
    }
}
