package com.handwoong.rainbowletter.image.exception;

import com.handwoong.rainbowletter.common.exception.BaseExceptionHandler;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import com.handwoong.rainbowletter.common.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ImageExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler({ImageUploadFailException.class})
    public ResponseEntity<ErrorResponse> duplicateEmail(final ImageUploadFailException exception) {
        final ErrorCode errorCode = exception.getErrorCode();
        logError(errorCode, exception);
        return createErrorResponse(errorCode);
    }
}
