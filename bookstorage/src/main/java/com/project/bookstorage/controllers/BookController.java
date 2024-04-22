package com.project.bookstorage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.project.bookstorage.dtos.BookResponse;
import com.project.bookstorage.exceptions.BookDoesNotExistException;
import com.project.bookstorage.exceptions.ExistingBookException;
import com.project.bookstorage.models.Book;
import com.project.bookstorage.services.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/addBook")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.addBook(book), HttpStatus.CREATED);
    }

    @GetMapping("/deleteBook/{isbn}")
    public ResponseEntity<String> deleteBook(@PathVariable int isbn) {
        return new ResponseEntity<>(bookService.deleteBook(isbn), HttpStatus.ACCEPTED);
    }

    @PutMapping("/updateBook")
    public ResponseEntity<String> updateBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.updateBook(book), HttpStatus.OK);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBookById(@PathVariable int isbn) {
        return new ResponseEntity<>(bookService.getBookById(isbn), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<BookResponse> getAllBooks(
        @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
            return new ResponseEntity<>(bookService.getAllBooks(pageNo, pageSize), HttpStatus.OK);
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

}