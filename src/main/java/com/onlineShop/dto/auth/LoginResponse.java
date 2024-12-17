package com.onlineShop.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class LoginResponse {

    private final String accessToken;

    public LoginResponse(final String accessToken) {
        this.accessToken = accessToken;
    }

}
