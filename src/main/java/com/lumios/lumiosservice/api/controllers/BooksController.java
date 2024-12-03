package com.lumios.lumiosservice.api.controllers;

import com.lumios.lumiosservice.api.requests.GoogleVisionRequest;
import com.lumios.lumiosservice.api.responses.BookFullResponse;
import com.lumios.lumiosservice.api.responses.BookShortResponse;
import com.lumios.lumiosservice.api.services.BooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BooksController {

    private final BooksService booksService;

    @GetMapping
    public BookShortResponse getBooksByQuery(
            @RequestParam String searchQuery,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return this.booksService.getBooksBySearchQuery(searchQuery, page, size);
    }

    @GetMapping("/{bookId}")
    public BookFullResponse getBookById(@PathVariable String bookId) {
        return this.booksService.getBookById(bookId);
    }

    @PostMapping("/vision")
    public BookShortResponse getBookByImageRecognition(@RequestBody GoogleVisionRequest googleVisionRequest) {
        return this.booksService.getBookByImageRecognition(googleVisionRequest);
    }

    @GetMapping("/trending")
    public BookShortResponse getTrendingBooks() {
        return this.booksService.getTrendingBooks();
    }
}
