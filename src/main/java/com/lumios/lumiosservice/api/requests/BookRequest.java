package com.lumios.lumiosservice.api.requests;

import lombok.Builder;

@Builder
public record BookRequest (String title, String eanCode) {
}
