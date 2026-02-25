package com.Bookhub.controller;

import com.Bookhub.entity.Book;
import com.Bookhub.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "Gestión de libros")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Lista todos los libros")
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @Operation(summary = "Obtiene un libro por id")
    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        return bookService.findById(id).orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    @Operation(summary = "Crea un libro")
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.save(book);
    }

    @Operation(summary = "Elimina un libro por id")
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}