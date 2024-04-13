package com.project.bookstorage.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookstorage.dtos.UserDto;
import com.project.bookstorage.dtos.UserResponse;
import com.project.bookstorage.exceptions.ExistingUserException;
import com.project.bookstorage.exceptions.UserDoesNotExistException;
import com.project.bookstorage.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody UserDto user) {
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable @NonNull Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/getUsers")
    public ResponseEntity<UserResponse> getAllUsers(
        @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo, 
        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return new ResponseEntity<>(userService.getAllUsers(pageNo, pageSize), HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable @NonNull Long id) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<String> updateUser(@PathVariable @NonNull Long id, @RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.updateUser(id, userDto), HttpStatus.OK);
    }


    //---------------------- Exception control ----------------------

    @ExceptionHandler(ExistingUserException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public String existingUser() {
        return "ERROR: Username already exists";
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public String inexistentUser() {
        return "ERROR: Username does not exist";
    }

}
