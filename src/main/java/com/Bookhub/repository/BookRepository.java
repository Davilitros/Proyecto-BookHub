package com.Bookhub.repository;

import com.Bookhub.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Buscar por patrón en el título
    List<Book> findByTitleContainingIgnoreCase(String pattern);

    // Buscar por patrón en autor
    List<Book> findByAuthorContainingIgnoreCase(String pattern);
    
    // Busqueda mas especifica
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String author, String desc);
    
}