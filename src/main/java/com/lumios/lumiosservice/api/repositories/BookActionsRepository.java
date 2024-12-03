package com.lumios.lumiosservice.api.repositories;

import com.lumios.lumiosservice.api.models.Rating;
import com.lumios.lumiosservice.api.models.RatingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookActionsRepository extends JpaRepository<Rating, RatingId> {
    @Query(
            value = "SELECT AVG(rating_value) AS average_rating FROM rating r WHERE r.book_id = ?1",
            nativeQuery = true)
    Float getAverageRating(Long bookId);
}
