package com.lumios.lumiosservice.api.mapper;

import com.lumios.lumiosservice.api.models.Book;
import com.lumios.lumiosservice.api.responses.BookFullResponse;
import com.lumios.lumiosservice.api.responses.BookShortDetailsDto;
import com.lumios.lumiosservice.api.responses.BookShortResponse;

import java.util.List;

public class BookMapper {

    private BookMapper() {}

    public static BookShortResponse mapBookToBookShortResponse(List<Book> bookList) {
        List<BookShortDetailsDto> bookShortDetailsList = bookList.stream()
                .map(BookMapper::mapBookToBookShortDetailsDto).toList();
        return BookShortResponse.builder().books(bookShortDetailsList).build();
    }

    public static BookFullResponse mapBookToBookFullResponse(Book book) {
        return BookFullResponse.builder()
                .id(book.getId().intValue())
                .title(book.getTitle())
                .author(book.getAuthor())
                .description(book.getDescription())
                .genre(book.getGenre())
                .thumbnail(book.getThumbnail())
                .pageCount(book.getPageCount())
                .publishedDate(book.getPublishedDate())
                .language(book.getLanguage())
                .rating(book.getRating())
                .build();
    }

    public static BookShortDetailsDto mapBookToBookShortDetailsDto(Book book) {
        return BookShortDetailsDto.builder()
                .id(String.valueOf(book.getId()))
                .title(book.getTitle())
                .author(book.getAuthor())
                .thumbnail(book.getThumbnail())
                .build();
    }
}
