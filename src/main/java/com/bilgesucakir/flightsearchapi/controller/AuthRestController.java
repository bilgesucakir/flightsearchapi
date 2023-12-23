    package com.bilgesucakir.flightsearchapi.controller;

    import com.bilgesucakir.flightsearchapi.dto.AuthResponseDTO;
    import com.bilgesucakir.flightsearchapi.entity.RoleEntity;
    import com.bilgesucakir.flightsearchapi.entity.UserEntity;
    import com.bilgesucakir.flightsearchapi.exception.UsernameInvalidException;
    import com.bilgesucakir.flightsearchapi.security.JWTGenerator;
    import com.bilgesucakir.flightsearchapi.service.RoleEntityService;
    import com.bilgesucakir.flightsearchapi.service.UserEntityService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import com.bilgesucakir.flightsearchapi.dto.UserRequestDTO;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.web.bind.annotation.*;

    import java.util.Collections;

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

        @PostMapping("/login")
        public ResponseEntity<AuthResponseDTO> login(@RequestBody UserRequestDTO userRequestDTO){

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRequestDTO.getUsername(), userRequestDTO.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtGenerator.generateToken(authentication);

            return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
        }

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
