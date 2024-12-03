package com.lumios.lumiosservice.config;

import com.lumios.lumiosservice.api.client.ISBNDBService;
import com.lumios.lumiosservice.api.client.types.BookShortISBNDB;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class MockISBNDBServiceConfig {

    @Bean
    public ISBNDBService isbndbService() {
        ISBNDBService mockService = Mockito.mock(ISBNDBService.class);
        String isbn = "9789038812915";
        BookShortISBNDB bookShortISBNDB = BookShortISBNDB.builder()
                .title("Effective Java")
                .imageUri("www.mock.nl")
                .build();

        Mockito.when(mockService.getBookTitleByIsbn(isbn)).thenReturn(Optional.of(bookShortISBNDB));
        return mockService;
    }
}