package com.handwoong.rainbowletter.mail.exception;

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
public class MailExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler({MailTemplateNotFoundException.class})
    public ResponseEntity<ErrorResponse> mailTemplateNotFound(final MailTemplateNotFoundException exception) {
        final ErrorCode errorCode = exception.getErrorCode();
        logWarn(errorCode, exception.getTemplateName());
        return createErrorResponse(errorCode);
    }

    @ExceptionHandler({MailSendFailException.class})
    public ResponseEntity<ErrorResponse> mailSendFail(final MailSendFailException exception) {
        final ErrorCode errorCode = ErrorCode.FAIL_SEND_MAIL;
        logError(errorCode, exception, exception.getEmail());
        return createErrorResponse(errorCode);
    }
}
