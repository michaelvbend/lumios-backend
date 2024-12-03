package com.lumios.lumiosservice.api.client.impl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lumios.lumiosservice.api.client.ISBNDBService;

import com.lumios.lumiosservice.api.client.types.BookShortISBNDB;
import com.lumios.lumiosservice.api.mapper.ISBNDBMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ISBNDBServiceImpl implements ISBNDBService {
    private static final String ISBNDB_API_URL = "https://api2.isbndb.com/book/";
    private final RestTemplate restTemplate;
    private final ISBNDBMapper isbndbMapper;

    @Value("${providers.isbndb.key}")
    private String apiKey;

    @Override
    public Optional<BookShortISBNDB> getBookTitleByIsbn(String isbn) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(ISBNDB_API_URL + isbn, HttpMethod.GET, entity, String.class);
            String responseBody = response.getBody();
            return Optional.of(isbndbMapper.mapToBookShortISBNDB(responseBody));
        } catch (RuntimeException | JsonProcessingException exception) {
            return Optional.empty();
        }
    }
}
