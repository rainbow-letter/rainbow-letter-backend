package com.handwoong.rainbowletter.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ErrorResponse> methodNotSupported(final HttpRequestMethodNotSupportedException exception) {
        final ErrorCode errorCode = ErrorCode.NOT_SUPPORT_METHOD;
        logWarn(errorCode, exception.getMessage());
        return createErrorResponse(errorCode);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> exception(final Exception exception) {
        final ErrorCode errorCode = ErrorCode.SERVER_ERROR;
        logError(errorCode, exception);
        return createErrorResponse(errorCode);
    }
}
