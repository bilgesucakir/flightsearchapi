package com.bilgesucakir.flightsearchapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.Jwts;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JWTGenerator {

    public String generateToken(Authentication authentication){
        String username = authentication.getName();

        Instant currentInstant =  Instant.now();
        Instant expirationInstant = currentInstant.plus(SecurityConstants.JWT_EXPIRATION, ChronoUnit.MILLIS);

        Date issueDate = Date.from(currentInstant);
        Date expirationDate = Date.from(expirationInstant);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(issueDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();

        System.out.println("New token: " + token);
        System.out.print("Issue date: " + issueDate);
        System.out.println("Expiration date: " + expirationDate);

        return token;
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {

        try {
            Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token);
            return true;
        }
        catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect.");
        }
    }

}
