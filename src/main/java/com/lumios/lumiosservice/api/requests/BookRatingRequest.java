package com.lumios.lumiosservice.api.requests;

public record BookRatingRequest(String bookId, String userId, Integer ratingValue) {
}