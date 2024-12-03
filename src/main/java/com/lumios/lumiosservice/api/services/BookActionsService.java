package com.lumios.lumiosservice.api.services;

import com.lumios.lumiosservice.api.exceptions.NoSuchBookExistsException;
import com.lumios.lumiosservice.api.exceptions.NoSuchUserExistsException;
import com.lumios.lumiosservice.api.models.Book;
import com.lumios.lumiosservice.api.models.Rating;
import com.lumios.lumiosservice.api.models.RatingId;
import com.lumios.lumiosservice.api.repositories.BookActionsRepository;
import com.lumios.lumiosservice.api.repositories.BookRepository;
import com.lumios.lumiosservice.api.requests.BookRatingRequest;
import com.lumios.lumiosservice.api.auth.user.User;
import com.lumios.lumiosservice.api.auth.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookActionsService {

    private final BookRepository bookRepository;
    private final BookActionsRepository bookActionsRepository;
    private final UserRepository userRepository;

    public void rateBookById(BookRatingRequest bookRatingRequest) {
        Book book = findBookById(bookRatingRequest.bookId());
        User user = findUserById(bookRatingRequest.userId());
        RatingId ratingId = new RatingId(book.getId(), user.getId());
        Rating rating = Rating.builder()
                .ratingId(ratingId)
                .book(book)
                .user(user)
                .ratingValue(bookRatingRequest.ratingValue())
                .build();
        this.bookActionsRepository.save(rating);
        book.setRating(calculateBookRating(book.getId()));
        this.bookRepository.save(book);
    }

    private float calculateBookRating(Long bookId) {
        return Optional.ofNullable(bookActionsRepository.getAverageRating(bookId)).orElse(0f);
    }

    private Book findBookById(String bookId) {
        return this.bookRepository.findById(Long.valueOf(bookId))
                .orElseThrow(() -> new NoSuchBookExistsException("No book found with id: " + bookId));
    }

    private User findUserById(String userId) {
        return this.userRepository.findById(Integer.valueOf(userId))
                .orElseThrow(() -> new NoSuchUserExistsException("No user found"));
    }
}
