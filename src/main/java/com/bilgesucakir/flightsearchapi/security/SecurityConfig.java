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

/**
 * Security configuration of the API handling authoriaztion for endpoint access according to roles, authentication status
 * by using a filter chain, enabling exception handling (auth related)
 * BCrypt is used to encrypt passwords
 * Airport and Flight rest controllers are set here for admin access only
 * Flight search with filter is allowed for users and admins
 * Register, login and Swagger OpenAPI endpoints are accessible to all
 */
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

                        .requestMatchers(HttpMethod.GET,
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/webjars/**"
                        ).permitAll()

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
