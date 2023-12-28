package com.handwoong.rainbowletter.faq.exception;

import com.handwoong.rainbowletter.common.exception.BaseExceptionHandler;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import com.handwoong.rainbowletter.common.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FAQExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler({FAQResourceNotFoundException.class})
    public ResponseEntity<ErrorResponse> faqResourceNotFound(final FAQResourceNotFoundException exception) {
        final ErrorCode errorCode = exception.getErrorCode();
        logWarn(errorCode, exception.getId().toString());
        return createErrorResponse(errorCode);
    }
}
