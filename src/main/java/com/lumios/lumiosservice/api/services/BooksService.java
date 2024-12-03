package com.lumios.lumiosservice.api.services;

import com.lumios.lumiosservice.api.client.GoogleVisionService;
import com.lumios.lumiosservice.api.client.ISBNDBService;
import com.lumios.lumiosservice.api.mapper.BookMapper;
import com.lumios.lumiosservice.api.client.types.BookShortISBNDB;
import com.lumios.lumiosservice.api.exceptions.ISBNNotFoundException;
import com.lumios.lumiosservice.api.exceptions.NoSuchBookExistsException;
import com.lumios.lumiosservice.api.models.Book;
import com.lumios.lumiosservice.api.repositories.BookRepository;
import com.lumios.lumiosservice.api.requests.GoogleVisionRequest;
import com.lumios.lumiosservice.api.responses.BookFullResponse;
import com.lumios.lumiosservice.api.responses.BookShortResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.lumios.lumiosservice.api.mapper.BookMapper.mapBookToBookShortResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class BooksService {

    private final BookRepository bookRepository;
    private final GoogleVisionService googleVisionService;
    private final ISBNDBService isbndbService;

    public BookShortResponse getBooksBySearchQuery(String searchQuery, int page, int size) {
        final PageRequest pageRequest = PageRequest.of(page, size);
        final List<Book> bookData = this.bookRepository.findBySearchStringInTitleOrAuthorOrGenreIgnoreCaseContaining(searchQuery, pageRequest);
        if (bookData.isEmpty()) {
            log.warn("No books found with search query: {}", searchQuery);
            return BookShortResponse.builder().books(Collections.emptyList()).build();
        }
        return mapBookToBookShortResponse(bookData);
    }

    public BookFullResponse getBookById(String bookId) {
        return bookRepository.findById(Long.valueOf(bookId))
                .map(BookMapper::mapBookToBookFullResponse)
                .orElseThrow(() -> new NoSuchBookExistsException("No book found with id: " + bookId));
    }

    public BookShortResponse getBookByImageRecognition(GoogleVisionRequest googleVisionRequest) {
        final String isbn = this.googleVisionService.getIsbnFromImage(googleVisionRequest);
        final Optional<BookShortISBNDB> bookShortISBNDB = this.isbndbService.getBookTitleByIsbn(isbn);
        return bookShortISBNDB
                .map(bookTitle -> getBooksBySearchQuery(bookTitle.title(), 0, 1))
                .orElseThrow(ISBNNotFoundException::new);
    }

    // TODO: ADD Caching mechanism to save values for a day and refresh on midnigh
    public BookShortResponse getTrendingBooks() {
        return mapBookToBookShortResponse(this.bookRepository.findTrendingBooks());
    }
}
