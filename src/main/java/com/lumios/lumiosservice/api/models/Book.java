package com.lumios.lumiosservice.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;

    @Column(columnDefinition="TEXT")
    private String description;
    private String genre;
    @JsonProperty("published_date")
    private String publishedDate;
    @JsonProperty("page_count")
    private String pageCount;
    private String language;
    private String thumbnail;
    private Float rating;
}
