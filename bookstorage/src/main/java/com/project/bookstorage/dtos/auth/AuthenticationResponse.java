package com.project.bookstorage.dtos.auth;

import java.io.Serializable;

import lombok.Data;

@Data
public class AuthenticationResponse implements Serializable {

    private String jwt;
}
