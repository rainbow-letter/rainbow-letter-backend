package com.handwoong.rainbowletter.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> methodArgumentNotValid(final MethodArgumentNotValidException exception) {
        final ErrorCode errorCode = ErrorCode.METHOD_ARGUMENT_NOT_VALID;
        final String errorMessage = parseFirstMessage(exception);
        logWarn(errorCode, errorMessage);
        return createErrorResponse(errorCode, errorMessage);
    }

    private String parseFirstMessage(final MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
    }
}
