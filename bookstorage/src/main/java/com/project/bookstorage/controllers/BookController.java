package com.project.bookstorage.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookstorage.exceptions.BookDoesNotExistException;
import com.project.bookstorage.exceptions.ExistingBookException;
import com.project.bookstorage.models.Book;
import com.project.bookstorage.services.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.addBook(book), HttpStatus.CREATED);
    }
}


//---------------------- Exception control ----------------------

@ExceptionHandler(ExistingBookException.class)
@ResponseStatus(code = HttpStatus.CONFLICT)
public String existingBook() {
    return "ERROR: Book already exists";
}

@ExceptionHandler(BookDoesNotExistException.class)
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public String inexistentBook() {
    return "ERROR: Book does not exist";
}