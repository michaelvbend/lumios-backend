package com.lumios.lumiosservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lumios.lumiosservice.LumiosServiceApplication;
import com.lumios.lumiosservice.api.auth.user.Role;
import com.lumios.lumiosservice.api.auth.user.User;
import com.lumios.lumiosservice.api.auth.user.UserRepository;
import com.lumios.lumiosservice.api.requests.BookRatingRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = LumiosServiceApplication.class
)
@ActiveProfiles("integration_test")
@AutoConfigureMockMvc(addFilters = false)
class BookActionsIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
        User user1 = User.builder()
                .id(1L)
                .email("test1@test.nl")
                .firstname("test")
                .lastname("test")
                .password("test")
                .role(Role.USER)
                .build();
        User user2 = User.builder()
                .id(2L)
                .email("test2@test.nl")
                .firstname("test")
                .lastname("test")
                .password("test")
                .role(Role.USER)
                .build();
        userRepository.saveAndFlush(user1);
        userRepository.saveAndFlush(user2);

    }

    @Test
    void whenRateBooks_thenGetBookWithUpdatedRating() throws Exception {
        BookRatingRequest bookRatingRequest1 = new BookRatingRequest("1", "1", 4);
        BookRatingRequest bookRatingRequest2 = new BookRatingRequest("1", "2", 5);

        mvc.perform(post("/api/v1/book-actions/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRatingRequest1)))
                .andExpect(status().isOk());

        mvc.perform(post("/api/v1/book-actions/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRatingRequest2)))
                .andExpect(status().isOk());

        mvc.perform(get("/api/v1/books/1"))
                .andExpect(jsonPath("$.rating").value("4.5"));
    }
}