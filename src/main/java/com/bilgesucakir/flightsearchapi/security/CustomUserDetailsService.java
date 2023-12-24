package com.bilgesucakir.flightsearchapi.security;

import com.bilgesucakir.flightsearchapi.entity.RoleEntity;
import com.bilgesucakir.flightsearchapi.entity.UserEntity;
import com.bilgesucakir.flightsearchapi.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A custom user details service implementing UserDetailsService to get user credentials from request and
 * assign them to a User object and map them to their roles for later use
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserEntityRepository userEntityRepository;

    @Autowired
    public CustomUserDetailsService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userEntityRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return new User(user.getUsername(), user.getPassword(), mapRolesRoAuthorities(user.getRoles()));
    }

    private Collection<GrantedAuthority> mapRolesRoAuthorities(List<RoleEntity> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
