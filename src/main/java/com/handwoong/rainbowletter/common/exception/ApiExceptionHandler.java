package com.handwoong.rainbowletter.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    private static final String MESSAGE_FORMAT = "[%s] %s %s";

    @ExceptionHandler({RainbowLetterException.class})
    public ResponseEntity<ErrorResponse> rainbowLetter(final RainbowLetterException exception) {
        final ErrorCode errorCode = exception.getErrorCode();
        logWarn(errorCode, exception, exception.getField());
        return createErrorResponse(errorCode);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(final ErrorCode errorCode) {
        final ErrorResponse errorResponse = ErrorResponse.from(errorCode);
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    private void logWarn(final ErrorCode errorCode, final Exception exception) {
        final String logMessage = String.format(
                MESSAGE_FORMAT, errorCode.name(), errorCode.getStatus(), errorCode.getMessage());
        log.warn(logMessage, exception);
    }

    private void logWarn(final ErrorCode errorCode, final Exception exception, final String field) {
        final String logMessage = String.format(
                MESSAGE_FORMAT, errorCode.name(), errorCode.getStatus(), errorCode.getMessage() + "-" + field);
        log.warn(logMessage, exception);
    }
}
