package com.project.bookstorage.dtos;

import java.util.List;

import com.project.bookstorage.models.Book;

import lombok.Data;

@Data
public class BookResponse {
    private List<Book> books;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
