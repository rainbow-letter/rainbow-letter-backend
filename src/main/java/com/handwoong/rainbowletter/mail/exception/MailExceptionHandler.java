package com.handwoong.rainbowletter.mail.exception;

import com.handwoong.rainbowletter.common.exception.BaseExceptionHandler;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import com.handwoong.rainbowletter.common.exception.ErrorResponse;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MailExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler({MessagingException.class})
    public ResponseEntity<ErrorResponse> duplicateEmail(final MessagingException exception) {
        final ErrorCode errorCode = ErrorCode.FAIL_SEND_MAIL;
        logError(errorCode, exception);
        return createErrorResponse(errorCode);
    }
}
