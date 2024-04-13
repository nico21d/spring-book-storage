package com.project.bookstorage.services;

import com.project.bookstorage.dtos.BookResponse;
import com.project.bookstorage.models.Book;

public interface BookService {
    String addBook(Book book);
    String deleteBook(int isbn);
    String updateBook(Book book);
    Book getBookById(int isbn);
    BookResponse getAllBooks(int pageNo, int pageSize);
}
