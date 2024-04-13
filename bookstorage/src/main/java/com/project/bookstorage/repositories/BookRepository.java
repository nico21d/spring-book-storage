package com.project.bookstorage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookstorage.models.Book;

public interface BookRepository extends JpaRepository<Book, Integer>{
}
