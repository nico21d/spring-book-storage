package com.project.bookstorage.dtos;

import java.io.Serializable;

import lombok.Data;

@Data
public class RegisteredUser implements Serializable {
    
    private Long id;
    private String username;
    private String role;
    private String jwt;
}
