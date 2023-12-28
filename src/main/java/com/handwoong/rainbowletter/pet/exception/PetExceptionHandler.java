package com.handwoong.rainbowletter.pet.exception;

import com.handwoong.rainbowletter.common.exception.BaseExceptionHandler;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import com.handwoong.rainbowletter.common.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PetExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler({PetResourceNotFoundException.class})
    public ResponseEntity<ErrorResponse> petResourceNotFound(final PetResourceNotFoundException exception) {
        final ErrorCode errorCode = exception.getErrorCode();
        logWarn(errorCode, exception.getId().toString());
        return createErrorResponse(errorCode);
    }
}
