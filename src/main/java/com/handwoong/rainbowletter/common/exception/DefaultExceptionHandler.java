package com.handwoong.rainbowletter.common.exception;

import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order
@RestControllerAdvice
public class DefaultExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ErrorResponse> methodNotSupported(final HttpRequestMethodNotSupportedException exception) {
        final ErrorCode errorCode = ErrorCode.NOT_SUPPORT_METHOD;
        logWarn(errorCode, exception.getMessage());
        return createErrorResponse(errorCode);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponse> httpMessageNotReadable(final HttpMessageNotReadableException exception) {
        final ErrorCode errorCode = ErrorCode.METHOD_ARGUMENT_NOT_VALID;
        logWarn(errorCode, exception.getMessage());
        return createErrorResponse(errorCode);
    }

    @ExceptionHandler({ConversionFailedException.class})
    public ResponseEntity<ErrorResponse> conversionFailed(final ConversionFailedException exception) {
        final ErrorCode errorCode = ErrorCode.INVALID_PARAM_VALUE;
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
