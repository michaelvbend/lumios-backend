package com.lumios.lumiosservice.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lumios.lumiosservice.api.client.impl.ISBNDBServiceImpl;
import com.lumios.lumiosservice.api.mapper.ISBNDBMapper;
import com.lumios.lumiosservice.api.client.types.BookShortISBNDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.when;

class ISBNDBServiceTests {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ISBNDBMapper isbndbMapper;

    @InjectMocks
    private ISBNDBServiceImpl isbndbService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBookByIsbn_Success() throws JsonProcessingException {
        String isbn = "0321356683";
        String responseBody = "mocked response";
        BookShortISBNDB bookShortISBNDB = BookShortISBNDB.builder()
                .title("Effective Java")
                .imageUri("www.mock.nl")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer your_token_here");

        String isbnUrl = "https://api2.isbndb.com/book/" + isbn;
        ResponseEntity<String> responseEntity = ResponseEntity.ok(responseBody);

        when(restTemplate.exchange(
                Mockito.eq(isbnUrl),
                Mockito.eq(HttpMethod.GET),
                Mockito.any(HttpEntity.class),
                Mockito.eq(String.class)
        )).thenReturn(responseEntity);

        when(isbndbMapper.mapToBookShortISBNDB(responseBody)).thenReturn(bookShortISBNDB);

        Optional<BookShortISBNDB> responses = isbndbService.getBookTitleByIsbn(isbn);

        assertTrue(responses.isPresent());
        assertEquals("Effective Java", responses.get().title());
    }

    @Test
    void testGetBookByIsbn_Exception() {
        String isbn = "0321356683";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer your_token_here");

        String isbnUrl = "https://api2.isbndb.com/book/" + isbn;

        when(restTemplate.exchange(
                Mockito.eq(isbnUrl),
                Mockito.eq(HttpMethod.GET),
                Mockito.any(HttpEntity.class),
                Mockito.eq(String.class)
        )).thenThrow(new RuntimeException());

        Optional<BookShortISBNDB> responses = isbndbService.getBookTitleByIsbn(isbn);

        assertTrue(responses.isEmpty());
    }
}