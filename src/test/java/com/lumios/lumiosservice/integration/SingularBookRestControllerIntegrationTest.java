package com.lumios.lumiosservice.integration;

import com.lumios.lumiosservice.LumiosServiceApplication;
import com.lumios.lumiosservice.api.models.Book;
import com.lumios.lumiosservice.api.repositories.BookRepository;
import com.lumios.lumiosservice.api.requests.GoogleVisionRequest;
import com.lumios.lumiosservice.config.MockGoogleVisionServiceConfig;
import com.lumios.lumiosservice.config.MockISBNDBServiceConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = LumiosServiceApplication.class
)
@ActiveProfiles(value="integration_test")
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {MockISBNDBServiceConfig.class, MockGoogleVisionServiceConfig.class})
public class SingularBookRestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        bookRepository.deleteAll();
        Book book = Book.builder().title("Effective Java")
                .author("Author")
                .description("Descprtion")
                .genre("Genre")
                .language("Language")
                .pageCount("300")
                .publishedDate("PublishedData")
                .thumbnail("thumbnail")
                .build();
        bookRepository.saveAndFlush(book);
    }

    @Test
    void givenBook_whenGetBookByVision_thenStatus200() throws Exception {
       String requestJson = objectMapper.writeValueAsString(createGoogleVisionRequestWithValidIsbn());

        mvc.perform(post("/api/v1/books/vision")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books[0].title").value("Effective Java"));
    }

    @Test
    void givenInvalidBook_whenGetBookByVision_thenStatus404() throws Exception {
        String requestJson = objectMapper.writeValueAsString(createGoogleVisionRequestWithInvalidIsbn());

        mvc.perform(post("/api/v1/books/vision")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound());
    }

    public static GoogleVisionRequest createGoogleVisionRequestWithValidIsbn() {
        GoogleVisionRequest.Image image = GoogleVisionRequest.Image.builder()
                .content("base64EncodedImageContent")
                .build();
        GoogleVisionRequest.Feature feature = GoogleVisionRequest.Feature.builder()
                .type("TEXT_DETECTION")
                .build();
        GoogleVisionRequest.ImageRequest imageRequest = GoogleVisionRequest.ImageRequest.builder()
                .image(image)
                .features(List.of(feature))
                .build();
        return GoogleVisionRequest.builder()
                .requests(List.of(imageRequest))
                .build();
    }

    public static GoogleVisionRequest createGoogleVisionRequestWithInvalidIsbn() {
        GoogleVisionRequest.Image image = GoogleVisionRequest.Image.builder()
                .content("nonExistentImageContent")
                .build();
        GoogleVisionRequest.Feature feature = GoogleVisionRequest.Feature.builder()
                .type("TEXT_DETECTION")
                .build();
        GoogleVisionRequest.ImageRequest imageRequest = GoogleVisionRequest.ImageRequest.builder()
                .image(image)
                .features(List.of(feature))
                .build();
        return GoogleVisionRequest.builder()
                .requests(List.of(imageRequest))
                .build();
    }
}