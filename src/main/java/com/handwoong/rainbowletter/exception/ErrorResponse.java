package com.handwoong.rainbowletter.exception;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record ErrorResponse(
        String name,
        String message,
        int status,
        String code,
        LocalDateTime timestamp
) {
    public static ResponseEntity<ErrorResponse> from(final ErrorCode errorCode) {
        final ErrorResponse errorResponse = create(errorCode, errorCode.getMessage());
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    public static ResponseEntity<ErrorResponse> of(final ErrorCode errorCode, final String message) {
        final ErrorResponse errorResponse = create(errorCode, message);
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    private static ErrorResponse create(final ErrorCode errorCode, final String message) {
        return ErrorResponse.builder()
                .name(errorCode.getStatus().name())
                .message(message)
                .status(errorCode.getStatus().value())
                .code(errorCode.name())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
