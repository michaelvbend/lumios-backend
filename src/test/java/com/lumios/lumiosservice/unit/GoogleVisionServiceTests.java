package com.lumios.lumiosservice.unit;

import com.lumios.lumiosservice.api.client.impl.GoogleVisionServiceImpl;
import com.lumios.lumiosservice.api.mapper.GoogleVisionMapper;
import com.lumios.lumiosservice.api.exceptions.ImageAnalysisException;
import com.lumios.lumiosservice.api.requests.GoogleVisionRequest;
import com.lumios.lumiosservice.api.responses.GoogleVisionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class GoogleVisionServiceTests {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private GoogleVisionMapper googleVisionMapper;

    @InjectMocks
    private GoogleVisionServiceImpl googleVisionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetIsbnFromImage_Success() throws RestClientException, IOException {
        GoogleVisionRequest request = GoogleVisionRequest.builder().build();
        String response = "mocked response";
        GoogleVisionResponse googleVisionResponse = new GoogleVisionResponse("978-3-16-148410-0");

        when(restTemplate.postForObject(anyString(), eq(request), eq(String.class)))
                .thenReturn(response);
        when(googleVisionMapper.mapToGoogleVisionResponse(response)).thenReturn(googleVisionResponse);

        String isbn = googleVisionService.getIsbnFromImage(request);

        assertEquals("9783161484100", isbn);
    }

    @Test
    void testGetIsbnFromImage_RestClientException() {
        GoogleVisionRequest request = GoogleVisionRequest.builder().build();

        when(restTemplate.postForObject(anyString(), eq(request), eq(String.class)))
                .thenThrow(new RestClientException("Error"));

        assertThrows(ImageAnalysisException.class, () -> googleVisionService.getIsbnFromImage(request));
    }

    @Test
    void testGetIsbnFromImage_IOException() throws IOException {
        GoogleVisionRequest request = GoogleVisionRequest.builder().build();
        String response = "mocked response";

        when(restTemplate.postForObject(anyString(), eq(request), eq(String.class)))
                .thenReturn(response);
        when(googleVisionMapper.mapToGoogleVisionResponse(response)).thenThrow(new IOException("Error"));

        assertThrows(ImageAnalysisException.class, () -> googleVisionService.getIsbnFromImage(request));
    }
}