package com.lumios.lumiosservice.api.client;

import com.lumios.lumiosservice.api.requests.GoogleVisionRequest;

public interface GoogleVisionService {
    String getIsbnFromImage(GoogleVisionRequest googleVisionRequest);
}
