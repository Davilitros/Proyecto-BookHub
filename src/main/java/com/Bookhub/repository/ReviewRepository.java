package com.Bookhub.repository;

import com.Bookhub.entity.Review;
import com.Bookhub.entity.Book;
import com.Bookhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByBook(Book book);

    List<Review> findByUser(User user);
}