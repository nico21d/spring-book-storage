package com.project.bookstorage.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookstorage.dtos.auth.AuthenticationRequest;
import com.project.bookstorage.dtos.auth.AuthenticationResponse;
import com.project.bookstorage.services.auth.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;
    
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
        @RequestBody @Validated AuthenticationRequest authenticationRequest) {
            AuthenticationResponse response = authenticationService.login(authenticationRequest);

            return ResponseEntity.ok(response);
    }
}
