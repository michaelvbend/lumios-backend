package com.lumios.lumiosservice.api.responses;

public record BookRatingResponse(Long id, Long bookId, Long userId, Integer rating) {
}
