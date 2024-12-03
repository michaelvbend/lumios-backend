package com.lumios.lumiosservice.api.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lumios.lumiosservice.api.client.types.BookShortISBNDB;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ISBNDBMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private ISBNDBMapper() {}

    public BookShortISBNDB mapToBookShortISBNDB(String response) throws JsonProcessingException {
        JsonNode root = objectMapper.readTree(response);
        JsonNode book = root.path("book");
        String title = book.path("title_long").textValue();
        String author = book.path("authors").get(0).textValue();
        String imageUri = book.path("image").textValue();
        return BookShortISBNDB.builder()
                .title(title)
                .author(author)
                .imageUri(imageUri)
                .build();
    }
}
