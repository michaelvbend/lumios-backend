package com.lumios.lumiosservice.config;

import com.lumios.lumiosservice.api.client.GoogleVisionService;
import com.lumios.lumiosservice.api.requests.GoogleVisionRequest;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MockGoogleVisionServiceConfig {

    @Bean
    public GoogleVisionService googleVisionService() {
        GoogleVisionService mockService = Mockito.mock(GoogleVisionService.class);
        GoogleVisionRequest request = GoogleVisionRequest.builder()
                .requests(List.of(
                        GoogleVisionRequest.ImageRequest.builder()
                                .image(GoogleVisionRequest.Image.builder().content("base64EncodedImageContent").build())
                                .features(List.of(GoogleVisionRequest.Feature.builder().type("TEXT_DETECTION").build()))
                                .build()
                ))
                .build();
        String isbn = "9789038812915";
        String isbnInvalid = "0000000000000";

        Mockito.when(mockService.getIsbnFromImage(request)).thenReturn(isbn);
        Mockito.when(mockService.getIsbnFromImage(Mockito.argThat(req -> req.requests().getFirst().image().content().equals("nonExistentImageContent"))))
                .thenReturn(isbnInvalid);
        return mockService;
    }
}