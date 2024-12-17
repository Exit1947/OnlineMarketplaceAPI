package com.onlineShop.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.onlineShop.models.Users.RolePrivilege.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtIssuer {

    private final JwtProperties prop;

        public String issue(String userId, String email, Role role, List<? extends GrantedAuthority> authorities){
            return JWT.create()
                    .withSubject(userId)
                        .withClaim("email", email)
                        .withClaim("role", role.getType().name())
                        .withArrayClaim("authorities", authorities.stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                    .withIssuedAt(new Date())
                    .withExpiresAt(Instant.now().plus(Duration.of(prop.getExpirationDaysOfToken(), ChronoUnit.DAYS)))
                    .withIssuer(prop.getServiceName())
                    .sign(Algorithm.HMAC256(prop.getSecret()));
    }
}
