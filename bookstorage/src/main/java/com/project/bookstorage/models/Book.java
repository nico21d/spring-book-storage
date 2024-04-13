package com.project.bookstorage.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "book_table")
public class Book {
    
    @Id
    private int isbn;
    private String title;
    private String author;
    private String genre;
    private Date publishing_date;
    private String language;
    private String saga;
}
