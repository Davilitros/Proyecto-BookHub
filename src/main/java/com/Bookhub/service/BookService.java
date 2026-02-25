package com.Bookhub.service;

import com.Bookhub.entity.Book;
import com.Bookhub.entity.User;
import com.Bookhub.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Crear libro
    public Book addBook(Book book, User user) {
        book.setUser(user);
        book.setCreatedAt(LocalDateTime.now());
        return bookRepository.save(book);
    }
    
    // Guardar libro
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    // Borrar libro por id
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    // Buscar por ID
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    // Listar todos
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    // Buscar por patrón
    public List<Book> search(String pattern) {
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrDescriptionContainingIgnoreCase(pattern, pattern, pattern);
    }
}