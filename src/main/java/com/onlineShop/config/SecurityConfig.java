package com.onlineShop.config;

import com.onlineShop.security.CustomUserDetailService;
import com.onlineShop.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailService customUserDetailService;

    public SecurityConfig(final JwtAuthenticationFilter jwtAuthenticationFilter,
                          final CustomUserDetailService customUserDetailService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customUserDetailService = customUserDetailService;
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable);
        http
                .authorizeHttpRequests(registry -> registry
                        //Auth api gateway's
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/registration").permitAll()

                        //Product api gateway's
                        .requestMatchers(HttpMethod.POST,"/api/product").hasAuthority("CREATE_PRODUCT")
                        .requestMatchers(HttpMethod.POST,"/api/product").hasRole("MODERATOR")
                        .requestMatchers(HttpMethod.POST,"/api/product").hasRole("ADMIN")
                        //-----------------------------------------------------------
                        .requestMatchers(HttpMethod.GET,"/api/product/id={id}").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/product/title={title}").permitAll()
                        //-----------------------------------------------------------
                        .requestMatchers(HttpMethod.GET,"/api/product/card/id={id}").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/product/card/title={title}").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/product/card/count={count}").permitAll()
                        //-----------------------------------------------------------
                        .requestMatchers(HttpMethod.PUT,"/api/product").hasAuthority("UPDATE_PRODUCT")
                        .requestMatchers(HttpMethod.PUT,"/api/product").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/product").hasRole("MODERATOR")
                        //-----------------------------------------------------------
                        .requestMatchers(HttpMethod.DELETE,"/api/product/id={id}").hasAuthority("DELETE_PRODUCT")
                        .requestMatchers(HttpMethod.DELETE,"/api/product/id={id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/product/id={id}").hasRole("MODERATOR")
                        //-----------------------------------------------------------
                        .requestMatchers(HttpMethod.POST, "/api/product/media/product-id={productId}").hasAuthority("SAVE_MEDIA")
                        .requestMatchers(HttpMethod.POST,"/api/product/media/product-id={productId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/product/media/thumbnail/product-id={productId}").hasAuthority("SAVE_MEDIA")
                        .requestMatchers(HttpMethod.POST,"/api/product/media/thumbnail/product-id={productId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/product/media/initial-save-media/product-id={productId}").hasAuthority("SAVE_MEDIA")
                        .requestMatchers(HttpMethod.POST,"/api/product/media/initial-save-media/product-id={productId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/product/all-media/product-id={productId}").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/product/media/media-name={mediaName}").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/api/product/media").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/product/media").hasAuthority("UPDATE_MEDIA")
                        .requestMatchers(HttpMethod.DELETE,"/api/product/media/media-id={mediaId}").hasAuthority("DELETE_MEDIA")
                        .requestMatchers(HttpMethod.DELETE,"/api/product/media/media-id={mediaId}").hasRole("ADMIN")

                        //UserEntity api gateway's
                        .requestMatchers(HttpMethod.POST,"/api/user/admin").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/user/admin/me").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/user/admin/id={id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/user/admin/email={email}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/user/admin/login={login}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/user/admin/phoneNumber={phoneNumber}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/user/admin").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/user/admin/id={id}").hasRole("ADMIN")
                        //-----------------------------------------------------------
                        .requestMatchers(HttpMethod.POST,"/api/user/moderator").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/user/moderator").hasAuthority("CREATE_MODERATOR")
                        .requestMatchers(HttpMethod.GET,"/api/user/moderator/me").hasRole("MODERATOR")
                        .requestMatchers(HttpMethod.GET,"/api/user/moderator/id={id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/user/moderator/id={id}").hasAuthority("GET_MODERATOR")
                        .requestMatchers(HttpMethod.GET,"/api/user/moderator/email={email}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/user/moderator/email={email}").hasAuthority("GET_MODERATOR")
                        .requestMatchers(HttpMethod.GET,"/api/user/moderator/login={login}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/user/moderator/login={login}").hasAuthority("GET_MODERATOR")
                        .requestMatchers(HttpMethod.GET,"/api/user/moderator/phoneNumber={phoneNumber}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/user/moderator/phoneNumber={phoneNumber}").hasAuthority("GET_MODERATOR")
                        .requestMatchers(HttpMethod.PUT,"/api/user/moderator").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/user/moderator").hasAuthority("UPDATE_MODERATOR")
                        .requestMatchers(HttpMethod.DELETE,"/api/user/moderator/id={id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/user/moderator/id={id}").hasAuthority("DELETE_MODERATOR")
                        //-----------------------------------------------------------
                        .requestMatchers(HttpMethod.POST,"/api/user/sales-rep").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/user/sales-rep").hasAuthority("CREATE_SALES_REP")
                        .requestMatchers(HttpMethod.GET,"/api/user/sales-rep/me").hasRole("SALES_REP")
                        .requestMatchers(HttpMethod.GET,"/api/user/sales-rep/id={id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/user/sales-rep/id={id}").hasAuthority("GET_SALES_REP")
                        .requestMatchers(HttpMethod.GET,"/api/user/sales-rep/email={email}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/user/sales-rep/email={email}").hasAuthority("GET_SALES_REP")
                        .requestMatchers(HttpMethod.GET,"/api/user/sales-rep/login={login}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/user/sales-rep/login={login}").hasAuthority("GET_SALES_REP")
                        .requestMatchers(HttpMethod.GET,"/api/user/sales-rep/phoneNumber={phoneNumber}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/user/sales-rep/phoneNumber={phoneNumber}").hasAuthority("GET_SALES_REP")
                        .requestMatchers(HttpMethod.PUT,"/api/user/sales-rep").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/user/sales-rep").hasAuthority("UPDATE_SALES_REP")
                        .requestMatchers(HttpMethod.DELETE,"/api/user/sales-rep/id={id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/user/sales-rep/id={id}").hasAuthority("DELETE_SALES_REP")
                        //-----------------------------------------------------------
                        .requestMatchers(HttpMethod.POST,"/api/user/end-user").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/user/end-user").hasAuthority("CREATE_END_USER")
                        .requestMatchers(HttpMethod.GET,"/api/user/end-user/me").hasRole("END_USER")
                        .requestMatchers(HttpMethod.GET,"/api/user/end-user/id={id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/user/end-user/id={id}").hasAuthority("GET_END_USER")
                        .requestMatchers(HttpMethod.GET,"/api/user/end-user/email={email}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/user/end-user/email={email}").hasAuthority("GET_END_USER")
                        .requestMatchers(HttpMethod.GET,"/api/user/end-user/login={login}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/user/end-user/login={login}").hasAuthority("GET_END_USER")
                        .requestMatchers(HttpMethod.GET,"/api/user/end-user/phoneNumber={phoneNumber}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/user/end-user/phoneNumber={phoneNumber}").hasAuthority("GET_END_USER")
                        .requestMatchers(HttpMethod.PUT,"/api/user/end-user/me").hasRole("END_USER")
                        .requestMatchers(HttpMethod.PUT,"/api/user/end-user").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/user/end-user").hasAuthority("UPDATE_END_USER")
                        .requestMatchers(HttpMethod.DELETE,"/api/user/end-user/me").hasRole("END_USER")
                        .requestMatchers(HttpMethod.DELETE,"/api/user/end-user/id={id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/user/end-user/id={id}").hasAuthority("DELETE_END_USER")
                        .requestMatchers(HttpMethod.GET, "/api/user/avatar/me").hasRole("END_USER")
                        .requestMatchers(HttpMethod.GET, "/api/user/avatar/user-id={userId}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/user/avatar/user-id={userId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/user/avatar/user-id={userId}").hasAuthority("UPDATE_AVATAR")
                        .requestMatchers(HttpMethod.POST, "/api/user/avatar/me").hasRole("END_USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/user/avatar/me").hasRole("END_USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/user/avatar/user-id={userId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/user/avatar/user-id={userId}").hasAuthority("UPDATE_AVATAR")

                        //Cart api gateway's
                        .requestMatchers(HttpMethod.GET,"/api/cart/me").hasRole("END_USER")
                        .requestMatchers(HttpMethod.POST,"/api/cart/item/me").hasRole("END_USER")
                        .requestMatchers(HttpMethod.PUT,"/api/cart/item/me").hasRole("END_USER")
                        .requestMatchers(HttpMethod.DELETE,"/api/cart/item/me").hasRole("END_USER")
                        .requestMatchers(HttpMethod.GET,"/api/cart/end-user-id={endUserId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/cart/end-user-id={endUserId}").hasAuthority("GET_CART")
                        .requestMatchers(HttpMethod.POST,"/api/cart/item/cart-id={cartId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/cart/item/cart-id={cartId}").hasAuthority("ADD_CART_ITEM")
                        .requestMatchers(HttpMethod.PUT,"/api/cart/item/cart-id={cartId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/cart/item/cart-id={cartId}").hasAuthority("UPDATE_CART_ITEM")
                        .requestMatchers(HttpMethod.DELETE,"/api/cart/item/cart-id={cartId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/cart/item/cart-id={cartId}").hasRole("DELETE_CART_ITEM")
                        .requestMatchers(HttpMethod.GET,"/api/cart/cart-id={cartId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/cart/cart-id={cartId}").hasRole("GET_CART")
                        .requestMatchers(HttpMethod.PUT,"/api/cart/cart-id={cartId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/cart/cart-id={cartId}").hasAuthority("DELETE_CART")
                        .requestMatchers(HttpMethod.DELETE,"/api/cart/cart-id={cartId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/cart/cart-id={cartId}").hasAuthority("DELETE_CART")

                        .anyRequest().denyAll()
                );
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(customUserDetailService);

        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
