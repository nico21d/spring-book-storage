package com.project.bookstorage.services.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.project.bookstorage.dtos.RegisteredUser;
import com.project.bookstorage.dtos.SaveUser;
import com.project.bookstorage.dtos.auth.AuthenticationRequest;
import com.project.bookstorage.dtos.auth.AuthenticationResponse;
import com.project.bookstorage.models.UserEntity;
import com.project.bookstorage.services.UserService;

@Service
public class AuthenticationService {

    private UserService userService;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    

    public AuthenticationService(UserService userService, JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public RegisteredUser registerUser(SaveUser newUser) {
        UserEntity user = userService.registerUser(newUser);

        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setId(user.getId());
        registeredUser.setUsername(user.getUsername());
        registeredUser.setRole(user.getRole().name());

        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        registeredUser.setJwt(jwt);

        return registeredUser;
    }

    private Map<String, Object> generateExtraClaims(UserEntity user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getUsername());
        extraClaims.put("role", user.getRole());
        extraClaims.put("authorities", user.getAuthorities());

        return extraClaims;
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            authenticationRequest.getUsername(), authenticationRequest.getPassword());

        authenticationManager.authenticate(authentication);

        UserEntity user = userService.findOneByUsername(authenticationRequest.getUsername()).get();
        String jwt = jwtService.generateToken(user,  generateExtraClaims(user));

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setJwt(jwt);

        return authenticationResponse;
    }
    
}
