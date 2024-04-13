package com.project.bookstorage.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.project.bookstorage.dtos.UserDto;
import com.project.bookstorage.dtos.UserResponse;
import com.project.bookstorage.exceptions.ExistingUserException;
import com.project.bookstorage.exceptions.UserDoesNotExistException;
import com.project.bookstorage.models.UserEntity;
import com.project.bookstorage.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    public String addUser(UserDto userDto) {
        if(!repo.existsByUsername(userDto.getUsername())) {
            UserEntity user = new UserEntity();
            user.setUsername(userDto.getUsername());
            repo.save(user);
            return user.getUsername();
        } else {
            throw new ExistingUserException();
        }
    }

    public UserDto getUserById(@NonNull Long id) {
        UserEntity user = repo.findById(id).orElseThrow(() -> new UserDoesNotExistException());
        return UserDto.fromUser(user);
    }

    public UserDto getUserByUsername(@NonNull String username) {
        UserEntity user = repo.findByUsername(username).orElseThrow(() -> new UserDoesNotExistException());
        return UserDto.fromUser(user);
    }

    public UserResponse getAllUsers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<UserEntity> users = repo.findAll(pageable);
        List<UserEntity> userList = users.getContent();
        List <UserDto> content = userList.stream().map(user -> UserDto.fromUser(user)).toList();

        UserResponse userResponse = new UserResponse();
        userResponse.setContent(content);
        userResponse.setPageNo(users.getNumber());
        userResponse.setPageSize(users.getSize());
        userResponse.setTotalElements(users.getTotalElements());
        userResponse.setTotalPages(users.getTotalPages());
        userResponse.setLast(users.isLast());
        
        return userResponse;
    }

    public String deleteUser(@NonNull Long id) {
        UserEntity user = repo.findById(id).orElseThrow(() -> new UserDoesNotExistException());
        repo.delete(user);
        return "Deleted user " + user.getUsername();

    }

    public String updateUser(@NonNull Long id, UserDto userDto) {
        UserEntity user = repo.findById(id).orElseThrow(() -> new UserDoesNotExistException());
        if(userDto != null) {
            user.setUsername(userDto.getUsername());
            repo.save(user);
        }
        return "Updated user with id " + user.getId();
    }
    
}
