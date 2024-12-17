package com.onlineShop.security;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class JwtToPrincipalConverter {

    private JwtToPrincipalConverter() {
    }

    public static UserPrincipal convert(final DecodedJWT jwt) {
         return UserPrincipal.builder()
                 .userId(jwt.getSubject())
                 .email(jwt.getClaim("email").asString())
                 .authorities(extractAuthoritiesFromClaim(jwt))
                 .build();
    }

    private static List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(final DecodedJWT jwt) {
        Claim claim = jwt.getClaim("authorities");
        if(claim.isNull() || claim.isMissing())
            return List.of();
        return claim.asList(SimpleGrantedAuthority.class);
    }
}
