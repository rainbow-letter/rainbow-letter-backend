package com.handwoong.rainbowletter.common.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExceptionLogger {
    private static final String MESSAGE_FORMAT = "[%s] %s %s";

    public void logWarn(final ErrorCode errorCode) {
        final String logMessage = String.format(
                MESSAGE_FORMAT, errorCode.name(), errorCode.getStatus(), errorCode.getMessage());
        log.warn(logMessage);
    }

    public void logWarn(final ErrorCode errorCode, final String field) {
        final String logMessage = String.format(
                MESSAGE_FORMAT, errorCode.name(), errorCode.getStatus(), errorCode.getMessage() + "-" + field);
        log.warn(logMessage);
    }

    public void logError(final ErrorCode errorCode, final Exception exception) {
        final String logMessage = String.format(
                MESSAGE_FORMAT, errorCode.name(), errorCode.getStatus(), errorCode.getMessage());
        log.error(logMessage, exception);
    }

    public void logError(final ErrorCode errorCode, final Exception exception, final String field) {
        final String logMessage = String.format(
                MESSAGE_FORMAT, errorCode.name(), errorCode.getStatus(), errorCode.getMessage() + "-" + field);
        log.error(logMessage, exception);
    }
}
