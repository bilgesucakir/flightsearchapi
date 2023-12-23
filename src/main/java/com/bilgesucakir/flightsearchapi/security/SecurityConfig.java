package com.bilgesucakir.flightsearchapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JWTAuthEntryPoint jwtAuthEntryPoint;
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JWTAuthEntryPoint jwtAuthEntryPoint) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http
                .csrf(AbstractHttpConfigurer::disable)

                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthEntryPoint))

                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers(HttpMethod.GET, "/api/search/flights").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "api/auth/**").permitAll()

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

                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(){
        return new JWTAuthenticationFilter();
    }
}
