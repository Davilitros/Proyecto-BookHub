package com.Bookhub.controller;

import com.Bookhub.entity.Book;
import com.Bookhub.entity.Review;
import com.Bookhub.entity.User;
import com.Bookhub.service.BookService;
import com.Bookhub.service.ReviewService;
import com.Bookhub.service.UserService;
import com.Bookhub.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private EmailService emailService;

    // Formulario para agregar reseña a un libro
    @GetMapping("/add/{bookId}")
    public String showAddReviewForm(@PathVariable Long bookId, Model model) {
        Book book = bookService.findById(bookId).orElseThrow();
        model.addAttribute("book", book);
        model.addAttribute("review", new Review());
        return "reviews/add"; // reviews/add.html
    }

    // Guardar reseña
    @PostMapping("/add/{bookId}")
    public String addReview(@PathVariable Long bookId,
                            @ModelAttribute Review review,
                            Authentication authentication) {
        Book book = bookService.findById(bookId).orElseThrow();
        User user = userService.findByUsername(authentication.getName()).orElseThrow();

        review.setBook(book);
        review.setUser(user);
        review.setCreatedAt(LocalDateTime.now());

        reviewService.save(review);

        emailService.sendReviewNotification(
                user.getEmail(),
                book.getTitle(),
                review.getComment()
        );

        return "redirect:/books";
    }

    // Eliminar reseña (solo ADMIN)
    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable Long id) {
        reviewService.deleteById(id);
        return "redirect:/books";
    }
}