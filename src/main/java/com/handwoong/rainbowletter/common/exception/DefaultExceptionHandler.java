package com.handwoong.rainbowletter.common.exception;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@RestControllerAdvice
@Order
public class DefaultExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> illegalArgumentException(final IllegalArgumentException exception) {
        final ErrorCode errorCode = ErrorCode.ILLEGAL_ARGUMENT;
        logError(errorCode, exception);
        return createErrorResponse(errorCode, exception.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> methodArgumentNotValid(final MethodArgumentNotValidException exception) {
        final ErrorCode errorCode = ErrorCode.METHOD_ARGUMENT_NOT_VALID;
        final String errorMessage = getValidationFirstMessage(exception);
        logWarn(errorCode, errorMessage);
        return createErrorResponse(errorCode, errorMessage);
    }

    private String getValidationFirstMessage(final MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
    }

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

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, ConversionFailedException.class})
    public ResponseEntity<ErrorResponse> invalidPathValue(final Exception exception) {
        final ErrorCode errorCode = ErrorCode.INVALID_PATH_VALUE;
        logWarn(errorCode, exception.getMessage());
        return createErrorResponse(errorCode);
    }

    @ExceptionHandler({MissingServletRequestPartException.class})
    public ResponseEntity<ErrorResponse> missingServletRequestPart(final MissingServletRequestPartException exception) {
        final ErrorCode errorCode = ErrorCode.REQUIRED_FILE;
        logWarn(errorCode, exception.getMessage());
        return createErrorResponse(errorCode);
    }

    @ExceptionHandler({MultipartException.class, FileUploadException.class})
    public ResponseEntity<ErrorResponse> multipart(final Exception exception) {
        final ErrorCode errorCode = ErrorCode.INVALID_FILE_CONTENT_TYPE;
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
