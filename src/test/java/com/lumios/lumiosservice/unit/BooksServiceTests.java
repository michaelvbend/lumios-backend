package com.lumios.lumiosservice.unit;

import com.lumios.lumiosservice.api.client.GoogleVisionService;
import com.lumios.lumiosservice.api.client.ISBNDBService;
import com.lumios.lumiosservice.api.client.types.BookShortISBNDB;
import com.lumios.lumiosservice.api.exceptions.ISBNNotFoundException;
import com.lumios.lumiosservice.api.exceptions.ImageAnalysisException;
import com.lumios.lumiosservice.api.exceptions.NoSuchBookExistsException;
import com.lumios.lumiosservice.api.models.Book;
import com.lumios.lumiosservice.api.repositories.BookRepository;
import com.lumios.lumiosservice.api.requests.GoogleVisionRequest;
import com.lumios.lumiosservice.api.responses.BookFullResponse;
import com.lumios.lumiosservice.api.responses.BookShortResponse;
import com.lumios.lumiosservice.api.services.BooksService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BooksServiceTests {

    @Mock
    private GoogleVisionService googleVisionService;

    @Mock
    private ISBNDBService isbndbService;

    @Mock
    private BookRepository bookRepository;

    private BooksService bookService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        bookService = new BooksService(bookRepository,
                googleVisionService, isbndbService);
    }

    @Test
    void testGetBooksByVision_Success() throws ImageAnalysisException {
        GoogleVisionRequest request = GoogleVisionRequest.builder().build();
        PageRequest pageRequest = PageRequest.of( 0,1);
        String isbn = "0321356683";
        BookShortISBNDB bookShortISBNDB = BookShortISBNDB.builder()
                .title("Effective Java")
                .build();
        List<Book> book = List.of(Book.builder()
                .id(1L)
                .title("Effective Java")
                .publishedDate("mock data")
                .pageCount("2")
                .language("nl")
                .genre("horror")
                .description("description")
                .author("Pieter Post")
                .id(1L)
                .thumbnail("google.nl")
                .build());

        when(googleVisionService.getIsbnFromImage(request)).thenReturn(isbn);
        when(isbndbService.getBookTitleByIsbn(isbn)).thenReturn(Optional.ofNullable(bookShortISBNDB));
        when(bookRepository.findBySearchStringInTitleOrAuthorOrGenreIgnoreCaseContaining("Effective Java", pageRequest)).thenReturn(book);

        BookShortResponse responses = bookService.getBookByImageRecognition(request);
        assertEquals("Effective Java", responses.books().getFirst().title());
    }

    @Test
    void testGetBooksByVision_NoBookFoundForIsbn() throws ImageAnalysisException {
        GoogleVisionRequest request = GoogleVisionRequest.builder().build();
        String isbn = "0321356683";

        when(googleVisionService.getIsbnFromImage(request)).thenReturn(isbn);
        when(isbndbService.getBookTitleByIsbn(isbn)).thenReturn(Optional.empty());

        assertThrows(ISBNNotFoundException.class, () -> bookService.getBookByImageRecognition(request));
    }

    @Test
    void testGetBookByQueryTitle_Success() {
        PageRequest pageRequest = PageRequest.of( 0,10);
        String title = "Mock title";
        Book book = Book.builder()
                .title("Mock title")
                .build();

        when(bookRepository.findBySearchStringInTitleOrAuthorOrGenreIgnoreCaseContaining(title, pageRequest)).thenReturn(List.of(book));

        BookShortResponse response = bookService.getBooksBySearchQuery(title, 0, 10);
        assertEquals(response.books().getFirst().title(), title);
    }

    @Test
    void testGetBookByQueryTitle_NotFound() {
        String title = "Rare title";
        PageRequest pageRequest = PageRequest.of( 0,10);
        BookShortResponse bookShortResponse = BookShortResponse.builder().books(Collections.emptyList()).build();
        when(bookRepository.findBySearchStringInTitleOrAuthorOrGenreIgnoreCaseContaining(title,pageRequest)).thenReturn(List.of());
        assertEquals( bookService.getBooksBySearchQuery(title, 0, 10), bookShortResponse);
    }

    @Test
    void testGetBooksById_Success() {
        Book book = Book.builder()
                .id(1L)
                .title("Effective Java")
                .publishedDate("mock data")
                .pageCount("2")
                .language("nl")
                .genre("horror")
                .description("description")
                .author("Pieter Post")
                .id(1L)
                .thumbnail("google.nl")
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookFullResponse responses = bookService.getBookById("1");

        assertEquals("Effective Java", responses.title());
    }

    @Test
    void testGetBooksById_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchBookExistsException.class, () -> bookService.getBookById("1"));
    }
}