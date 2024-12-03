package com.lumios.lumiosservice.api.responses;

import lombok.Builder;

@Builder
public record GoogleVisionResponse(String text) {
}