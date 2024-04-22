package com.project.bookstorage.dtos;

import java.io.Serializable;

import lombok.Data;

@Data
public class SaveUser implements Serializable {
    
    private String username;
    private String password;
    private String repeatedPassword;
}
