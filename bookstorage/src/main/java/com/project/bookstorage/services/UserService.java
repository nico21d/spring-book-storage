package com.project.bookstorage.services;

import org.springframework.lang.NonNull;

import com.project.bookstorage.dtos.UserDto;
import com.project.bookstorage.dtos.UserResponse;

public interface UserService {
    String addUser(UserDto userDto);
    UserDto getUserById(@NonNull Long id);
    UserDto getUserByUsername(@NonNull String username);
    UserResponse getAllUsers(int pageNo, int pageSize);
    String deleteUser(@NonNull Long id);
    String updateUser(@NonNull Long id, UserDto userDto);
    String addBook(@NonNull Long id, long isbn);
}
