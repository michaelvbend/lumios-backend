package com.lumios.lumiosservice.api.controllers;

import com.lumios.lumiosservice.api.requests.BookRatingRequest;
import com.lumios.lumiosservice.api.services.BookActionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/book-actions")
@RequiredArgsConstructor
public class BookActionsController {

    private final BookActionsService bookActionsService;

    @PostMapping("/rate")
    public void rateBookById(@RequestBody BookRatingRequest bookRatingRequest) {
        this.bookActionsService.rateBookById(bookRatingRequest);
    }
}
