package com.lumios.lumiosservice.api.models;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class RatingId implements Serializable {
    private Long bookId;
    private Long userId;

    public RatingId() {}

    public RatingId(Long bookId, Long userId) {
        this.bookId = bookId;
        this.userId = userId;
    }
}
