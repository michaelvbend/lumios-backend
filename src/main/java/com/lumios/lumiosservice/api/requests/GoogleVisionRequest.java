package com.lumios.lumiosservice.api.requests;

import lombok.Builder;
import java.util.List;

@Builder
public record GoogleVisionRequest(List<ImageRequest> requests) {

    @Builder
    public record ImageRequest(Image image, List<Feature> features) {
    }

    @Builder
    public record Image(String content) {
    }

    @Builder
    public record Feature(String type) {
    }
}