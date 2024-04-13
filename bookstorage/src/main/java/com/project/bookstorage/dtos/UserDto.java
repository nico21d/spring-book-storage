package com.project.bookstorage.dtos;

import com.project.bookstorage.models.UserEntity;

import lombok.Data;

@Data
public class UserDto {
    private String username;

    public static UserDto fromUser(UserEntity user) {
        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());

        return dto;
    }
}
