package com.Bookhub.controller;

import com.Bookhub.entity.Review;
import com.Bookhub.entity.Book;
import com.Bookhub.service.ReviewService;
import com.Bookhub.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Reviews", description = "Gestión de reseñas")
public class ReviewRestController {

    private final ReviewService reviewService;
    private final BookService bookService;

    public ReviewRestController(ReviewService reviewService, BookService bookService) {
        this.reviewService = reviewService;
        this.bookService = bookService;
    }

    @Operation(summary = "Lista todas las reseñas de un libro")
    @GetMapping("/book/{bookId}")
    public List<Review> getReviewsByBook(@PathVariable Long bookId) {
        Book book = bookService.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        return reviewService.findByBook(book);
    }

    @Operation(summary = "Crea una reseña para un libro")
    @PostMapping("/book/{bookId}")
    public Review addReview(@PathVariable Long bookId, @RequestBody Review review) {
        Book book = bookService.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        review.setBook(book);
        return reviewService.save(review);
    }

    @Operation(summary = "Elimina una reseña por id")
    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteById(id);
    }
}