package com.bilgesucakir.flightsearchapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests()

                .requestMatchers(HttpMethod.GET, "/api/search/flights").hasAnyRole("USER", "ADMIN")

                .requestMatchers(HttpMethod.GET, "api/airports").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "api/airports/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "api/airports").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "api/airports/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "api/airports/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "api/flights").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "api/flights/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "api/flights").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "api/flights/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "api/flights/**").hasRole("ADMIN")

                .anyRequest().authenticated()
                .and()
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService inMemoryUsers(){
        UserDetails admin = User.builder().username("admin").password("{noop}admin12345").roles("ADMIN").build();

        UserDetails user1 = User.builder().username("user1").password("{noop}user1abc").roles("USER").build();

        return new InMemoryUserDetailsManager(admin, user1);
    }
}
