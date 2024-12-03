package com.lumios.lumiosservice.api.client;

import com.lumios.lumiosservice.api.client.types.BookShortISBNDB;

import java.util.Optional;

public interface ISBNDBService {
    Optional<BookShortISBNDB> getBookTitleByIsbn(String isbn);
}
