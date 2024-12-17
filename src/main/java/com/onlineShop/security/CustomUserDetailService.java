package com.onlineShop.security;

import com.onlineShop.converter.users.AuthConverter;
import com.onlineShop.models.Users.UserEntity;
import com.onlineShop.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserEntityService userEntityService;

    @Autowired
    public CustomUserDetailService(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userEntityService.findByEmail(username).orElseThrow();
        return AuthConverter.personToUserPrincipal(userEntity);
    }
}
