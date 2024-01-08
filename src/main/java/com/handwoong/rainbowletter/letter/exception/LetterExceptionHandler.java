package com.handwoong.rainbowletter.letter.exception;

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
public class LetterExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler({SummaryFormatNotValidException.class})
    public ResponseEntity<ErrorResponse> summaryFormatNotValid(final SummaryFormatNotValidException exception) {
        final ErrorCode errorCode = exception.getErrorCode();
        logWarn(errorCode, exception.getSummary());
        return createErrorResponse(errorCode);
    }

    @ExceptionHandler({ContentFormatNotValidException.class})
    public ResponseEntity<ErrorResponse> contentFormatNotValid(final ContentFormatNotValidException exception) {
        final ErrorCode errorCode = exception.getErrorCode();
        logWarn(errorCode, exception.getContent());
        return createErrorResponse(errorCode);
    }

    @ExceptionHandler({LetterResourceNotFoundException.class})
    public ResponseEntity<ErrorResponse> letterResourceNotFound(final LetterResourceNotFoundException exception) {
        final ErrorCode errorCode = exception.getErrorCode();
        logWarn(errorCode, exception.getId().toString());
        return createErrorResponse(errorCode);
    }

    @ExceptionHandler({ReplyResourceNotFoundException.class})
    public ResponseEntity<ErrorResponse> replyResourceNotFound(final ReplyResourceNotFoundException exception) {
        final ErrorCode errorCode = exception.getErrorCode();
        logWarn(errorCode, exception.getId().toString());
        return createErrorResponse(errorCode);
    }
}
