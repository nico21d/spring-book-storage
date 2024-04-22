package com.project.bookstorage.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.bookstorage.dtos.SaveUser;
import com.project.bookstorage.dtos.UserDto;
import com.project.bookstorage.dtos.UserResponse;
import com.project.bookstorage.exceptions.BookDoesNotExistException;
import com.project.bookstorage.exceptions.UserDoesNotExistException;
import com.project.bookstorage.models.Book;
import com.project.bookstorage.models.UserEntity;
import com.project.bookstorage.models.util.Role;
import com.project.bookstorage.repositories.BookRepository;
import com.project.bookstorage.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity registerUser(SaveUser newUser) {

        UserEntity user = new UserEntity();
        user.setUsername(newUser.getUsername());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    public UserDto getUserById(@NonNull Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException());
        return UserDto.fromUser(user);
    }

    public UserDto getUserByUsername(@NonNull String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UserDoesNotExistException());
        return UserDto.fromUser(user);
    }

    public UserResponse getAllUsers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<UserEntity> users = userRepository.findAll(pageable);
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
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException());
        userRepository.delete(user);
        return "Deleted user " + user.getUsername();

    }

    public String updateUser(@NonNull Long id, UserDto userDto) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException());
        if(userDto != null) {
            user.setUsername(userDto.getUsername());
            userRepository.save(user);
        }
        return "Updated user with id " + user.getId();
    }

    public String addBook(@NonNull Long id, long isbn) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException());
        Book book = bookRepository.findById(isbn).orElseThrow(() -> new BookDoesNotExistException());
        Set<Book> userBooks = user.getBooks();
        userBooks.add(book);
        user.setBooks(userBooks);
        userRepository.save(user);

        return "Added " + book.getTitle() + " to " + user.getUsername();
    }

    public Optional<UserEntity> findOneByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
}
