package com.project.bookstorage.services;

import java.util.Optional;

import org.springframework.lang.NonNull;

import com.project.bookstorage.dtos.SaveUser;
import com.project.bookstorage.dtos.UserDto;
import com.project.bookstorage.dtos.UserResponse;
import com.project.bookstorage.models.UserEntity;

public interface UserService {
    UserEntity registerUser(SaveUser newUser);
    UserDto getUserById(@NonNull Long id);
    UserDto getUserByUsername(@NonNull String username);
    UserResponse getAllUsers(int pageNo, int pageSize);
    String deleteUser(@NonNull Long id);
    String updateUser(@NonNull Long id, UserDto userDto);
    String addBook(@NonNull Long id, long isbn);
    Optional<UserEntity> findOneByUsername(String username);
}
