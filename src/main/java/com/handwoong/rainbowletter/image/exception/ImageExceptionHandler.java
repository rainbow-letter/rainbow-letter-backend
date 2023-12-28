package com.handwoong.rainbowletter.image.exception;

import com.handwoong.rainbowletter.common.exception.BaseExceptionHandler;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import com.handwoong.rainbowletter.common.exception.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ImageExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler({ImageResourceNotFoundException.class})
    public ResponseEntity<ErrorResponse> imageResourceNotFound(final ImageResourceNotFoundException exception) {
        final ErrorCode errorCode = exception.getErrorCode();
        logWarn(errorCode, exception.getId().toString());
        return createErrorResponse(errorCode);
    }

    @ExceptionHandler({ImageUploadFailException.class})
    public ResponseEntity<ErrorResponse> imageUploadFail(final ImageUploadFailException exception) {
        final ErrorCode errorCode = exception.getErrorCode();
        logError(errorCode, exception);
        return createErrorResponse(errorCode);
    }
}
