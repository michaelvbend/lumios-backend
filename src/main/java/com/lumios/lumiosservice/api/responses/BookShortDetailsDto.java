package com.lumios.lumiosservice.api.responses;

import lombok.Builder;

@Builder
public record BookShortDetailsDto(String id, String title, String thumbnail, String author) {}

