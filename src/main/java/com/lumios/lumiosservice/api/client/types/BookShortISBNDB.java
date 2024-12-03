package com.lumios.lumiosservice.api.client.types;

import lombok.Builder;

@Builder
public record BookShortISBNDB(String title, String author, String imageUri) {
}
