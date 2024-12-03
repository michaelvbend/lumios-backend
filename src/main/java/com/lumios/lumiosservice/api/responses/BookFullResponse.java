package com.lumios.lumiosservice.api.responses;

import lombok.Builder;

@Builder
public record BookFullResponse( Integer id,
         String title,
         String author,
         String description,
         String genre,
         String thumbnail,
         String publishedDate,
         String pageCount,
         String language,
         Float rating)
{}