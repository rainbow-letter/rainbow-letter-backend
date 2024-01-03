package com.handwoong.rainbowletter.common.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseExceptionHandler extends ExceptionLogger {
    public ResponseEntity<ErrorResponse> createErrorResponse(final ErrorCode errorCode) {
        final ErrorResponse errorResponse = ErrorResponse.from(errorCode);
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    public ResponseEntity<ErrorResponse> createErrorResponse(final ErrorCode errorCode, final String errorMessage) {
        final ErrorResponse errorResponse = ErrorResponse.of(errorCode, errorMessage);
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }
}
