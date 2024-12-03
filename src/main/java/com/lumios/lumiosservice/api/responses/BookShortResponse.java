package com.lumios.lumiosservice.api.responses;

import lombok.Builder;

import java.util.List;

@Builder
public record BookShortResponse(List<BookShortDetailsDto> books){
}