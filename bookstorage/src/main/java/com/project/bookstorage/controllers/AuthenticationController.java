package com.project.bookstorage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookstorage.dtos.auth.AuthenticationRequest;
import com.project.bookstorage.dtos.auth.AuthenticationResponse;
import com.project.bookstorage.services.auth.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("validate")
    public ResponseEntity<Boolean> validate(@RequestParam String jwt) {
        boolean isValid = authenticationService.validateToken(jwt);
        return new ResponseEntity<>(isValid, HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
        @RequestBody @Validated AuthenticationRequest authenticationRequest) {
            AuthenticationResponse response = authenticationService.login(authenticationRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
