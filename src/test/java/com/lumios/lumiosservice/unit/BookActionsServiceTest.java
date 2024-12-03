package com.lumios.lumiosservice.unit;

import com.lumios.lumiosservice.api.exceptions.NoSuchBookExistsException;
import com.lumios.lumiosservice.api.models.Book;
import com.lumios.lumiosservice.api.models.Rating;
import com.lumios.lumiosservice.api.repositories.BookActionsRepository;
import com.lumios.lumiosservice.api.repositories.BookRepository;
import com.lumios.lumiosservice.api.requests.BookRatingRequest;
import com.lumios.lumiosservice.api.services.BookActionsService;
import com.lumios.lumiosservice.api.auth.user.User;
import com.lumios.lumiosservice.api.auth.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookActionsServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookActionsRepository bookActionsRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookActionsService bookActionsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void rateBookByIdSuccessfully() {
        Book book = Book.builder().id(1L).build();
        User user = User.builder().id(1L).build();
        BookRatingRequest request = new BookRatingRequest("1", "1", 5);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(bookActionsRepository.getAverageRating(1L)).thenReturn(4.5f);

        bookActionsService.rateBookById(request);

        verify(bookActionsRepository, times(1)).save(any(Rating.class));
        verify(bookRepository, times(1)).save(book);
        assertEquals(4.5f, book.getRating());
    }

    @Test
    void rateBookByIdBookNotFound() {
        BookRatingRequest request = new BookRatingRequest("1", "1", 5);

        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchBookExistsException.class, () -> bookActionsService.rateBookById(request));
    }
}