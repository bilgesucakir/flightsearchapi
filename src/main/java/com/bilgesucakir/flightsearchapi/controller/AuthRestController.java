package com.bilgesucakir.flightsearchapi.controller;

import com.bilgesucakir.flightsearchapi.dto.AuthResponseDTO;
import com.bilgesucakir.flightsearchapi.entity.RoleEntity;
import com.bilgesucakir.flightsearchapi.entity.UserEntity;
import com.bilgesucakir.flightsearchapi.exception.UsernameInvalidException;
import com.bilgesucakir.flightsearchapi.exception.handling.CustomErrorResponse;
import com.bilgesucakir.flightsearchapi.security.JWTGenerator;
import com.bilgesucakir.flightsearchapi.service.RoleEntityService;
import com.bilgesucakir.flightsearchapi.service.UserEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.bilgesucakir.flightsearchapi.dto.UserRequestDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * REST Controller for auth operations. Registration of unauthenticated users and login of registered users are handled.
 * Accessing these endpoints does not require any authorization.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private UserEntityService userEntityService;
    private RoleEntityService roleEntityService;
    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthRestController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
                              UserEntityService userEntityService, RoleEntityService roleEntityService,
                              JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userEntityService = userEntityService;
        this.roleEntityService = roleEntityService;
        this.jwtGenerator = jwtGenerator;
    }

    @Operation(summary = "POST request performing login for unauthorized user", description = "A JWT is generated that is valid for 15 minutes",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"
                    ),
                    @ApiResponse(description = "Internal Server Error<br>-Bad credentials", responseCode = "500",
                            content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))})
            })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody UserRequestDTO userRequestDTO){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRequestDTO.getUsername(), userRequestDTO.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication);

        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    @Operation(summary = "POST request registering the unauthenticated user", description = "User is saved into database and USER role is assigned to the newly generated user",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"
                    ),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))}
                    ),
                    @ApiResponse(description = "Bad Request<br>-Username already exists<br>-Invalid username", responseCode = "400",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))})
            })
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRequestDTO userRequestDTO){

        if(userEntityService.isUserEntityExists(userRequestDTO.getUsername())){
            throw new UsernameInvalidException("Cannot add user. Username already exists.");
        }

        if(!userEntityService.isUsernameValid(userRequestDTO.getUsername())){
            throw new UsernameInvalidException("Cannot register user. Invalid username.");
        }

        UserEntity user = new UserEntity();
        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        RoleEntity role = roleEntityService.findByName("ROLE_USER");
        user.setRoles(Collections.singletonList(role));

        userEntityService.save(user);

        return new ResponseEntity<>("User registered successfully.", HttpStatus.OK);
    }
}
