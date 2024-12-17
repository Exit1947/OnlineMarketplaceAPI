package com.onlineShop.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;

    @Autowired
    public JwtAuthenticationFilter(final JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        extractTokenFromRequest(request)
                .map(jwtDecoder::decode)
                .map(JwtToPrincipalConverter::convert)
                .map(UserPrincipalAuthenticationToken::new)
                .ifPresent(authenticationToken -> {
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                });
            filterChain.doFilter(request, response);
    }

    private Optional<String> extractTokenFromRequest(final HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return Optional.of(authHeader.substring(7));
        }
        return Optional.empty();
    }

}
