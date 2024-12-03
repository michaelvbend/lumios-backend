package com.lumios.lumiosservice.api.client.impl;

import com.lumios.lumiosservice.api.client.GoogleVisionService;
import com.lumios.lumiosservice.api.exceptions.ImageAnalysisException;
import com.lumios.lumiosservice.api.mapper.GoogleVisionMapper;
import com.lumios.lumiosservice.api.requests.GoogleVisionRequest;
import com.lumios.lumiosservice.api.responses.GoogleVisionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleVisionServiceImpl implements GoogleVisionService {
    private static final int ISBN_LENGTH = 13;
    private static final String ISBN_STARTING_NUM = "9";
    private static final String GOOGLE_API_URL = "https://vision.googleapis.com/v1/images:annotate?key=";

    @Value("${providers.google.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final GoogleVisionMapper googleVisionMapper;

    @Override
    public String getIsbnFromImage(GoogleVisionRequest googleVisionRequest) {
        try {
            String response = restTemplate.postForObject(GOOGLE_API_URL + apiKey, googleVisionRequest, String.class);
            return this.extractISBN(googleVisionMapper.mapToGoogleVisionResponse(response));
        } catch (RestClientException | IOException exception) {
            throw new ImageAnalysisException("Failed to analyse image");
        }
    }

    private String extractISBN(GoogleVisionResponse googleVisionResponse) {
        String isbnTrimmed = googleVisionResponse.text()
                .replace(" ", "")
                .replace("-", "");

        int indexOfFirstNineOccurrence = isbnTrimmed.indexOf(ISBN_STARTING_NUM);
        return isbnTrimmed.substring(indexOfFirstNineOccurrence, indexOfFirstNineOccurrence + ISBN_LENGTH );
    }
}
