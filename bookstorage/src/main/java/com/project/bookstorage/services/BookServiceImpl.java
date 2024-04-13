package com.project.bookstorage.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.project.bookstorage.dtos.BookResponse;
import com.project.bookstorage.exceptions.BookDoesNotExistException;
import com.project.bookstorage.exceptions.ExistingBookException;
import com.project.bookstorage.models.Book;
import com.project.bookstorage.repositories.BookRepository;

public class BookServiceImpl implements BookService{

    private BookRepository repo;

    public BookServiceImpl(BookRepository repo) {
        this.repo = repo;
    }

    public String addBook(Book book) {
        if(!repo.existsById(book.getIsbn())) {
            repo.save(book);
            return book.getTitle();
        } else {
            throw new ExistingBookException();
        }
    }

    public String deleteBook(int isbn) {
        Book book = repo.findById(isbn).orElseThrow(() -> new BookDoesNotExistException());
        repo.delete(book);
        return book.getTitle();
    }

    public String updateBook(Book book) {
        if(repo.existsById(book.getIsbn())) {
            repo.save(book);
            return book.getTitle();
        } else {
            throw new BookDoesNotExistException();
        }
    }

    public Book getBookById(int isbn) {
        Book book = repo.findById(isbn).orElseThrow(() -> new BookDoesNotExistException());
        return book;
    }

    public BookResponse getAllBooks(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Book> books = repo.findAll(pageable);
        List<Book> bookList = books.getContent();

        BookResponse bookResponse = new BookResponse();
        bookResponse.setBooks(bookList);
        bookResponse.setPageNo(books.getNumber());
        bookResponse.setPageSize(books.getSize());
        bookResponse.setTotalElements(books.getTotalElements());
        bookResponse.setTotalPages(books.getTotalPages());
        bookResponse.setLast(books.isLast());

        return bookResponse;
    }
    
}
