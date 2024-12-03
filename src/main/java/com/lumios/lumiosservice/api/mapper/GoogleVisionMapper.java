package com.lumios.lumiosservice.api.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lumios.lumiosservice.api.responses.GoogleVisionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class GoogleVisionMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private GoogleVisionMapper() {}

    public GoogleVisionResponse mapToGoogleVisionResponse(String response) throws IOException {
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode textAnnotations = root.path("responses").get(0).path("textAnnotations");
            if (textAnnotations.isArray() && !textAnnotations.isEmpty()) {
                String description = textAnnotations.get(0).path("description").asText();
                return GoogleVisionResponse.builder().text(description).build();
            }
            return GoogleVisionResponse.builder().text("No text found in image").build();
        } catch (IOException e) {
            log.error("Error parsing response from Google Vision API", e);
            throw new IOException("Failed to map Google Vision Response: ", e);
        }
    }
}
