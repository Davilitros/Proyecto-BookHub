package com.Bookhub.service;

import com.Bookhub.entity.Book;
import com.Bookhub.entity.Review;
import com.Bookhub.entity.User;
import com.Bookhub.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    // Crear reseña
    public Review addReview(Review review, User user, Book book) {
        review.setUser(user);
        review.setBook(book);
        return reviewRepository.save(review);
    }

    // Listar reseñas de un libro
    public List<Review> getReviewsByBook(Book book) {
        return reviewRepository.findByBook(book);
    }

    // Listar reseñas de un usuario
    public List<Review> getReviewsByUser(User user) {
        return reviewRepository.findByUser(user);
    }
    
    public List<Review> findByBook(Book book) {
        return reviewRepository.findByBook(book);
    }
    
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }

    public Optional<Review> findById(Long id) {
        return reviewRepository.findById(id);
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }
}