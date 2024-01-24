package com.handwoong.rainbowletter.member.exception;

import com.handwoong.rainbowletter.common.exception.BaseExceptionHandler;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import com.handwoong.rainbowletter.common.exception.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MemberExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler({UnAuthorizationException.class})
    public ResponseEntity<ErrorResponse> unAuthorization(final UnAuthorizationException exception) {
        final ErrorCode errorCode = exception.getErrorCode();
        logWarn(errorCode);
        return createErrorResponse(errorCode);
    }
    
    @ExceptionHandler({DuplicateEmailException.class})
    public ResponseEntity<ErrorResponse> duplicateEmail(final DuplicateEmailException exception) {
        final ErrorCode errorCode = exception.getErrorCode();
        logWarn(errorCode, exception.getEmail());
        return createErrorResponse(errorCode);
    }

    @ExceptionHandler({EmailFormatNotValidException.class})
    public ResponseEntity<ErrorResponse> emailFormatNotValid(final EmailFormatNotValidException exception) {
        final ErrorCode errorCode = exception.getErrorCode();
        logWarn(errorCode, exception.getEmail());
        return createErrorResponse(errorCode);
    }

    @ExceptionHandler({MemberEmailNotFoundException.class})
    public ResponseEntity<ErrorResponse> memberEmailNotFound(final MemberEmailNotFoundException exception) {
        final ErrorCode errorCode = exception.getErrorCode();
        logWarn(errorCode, exception.getEmail());
        return createErrorResponse(errorCode);
    }

    @ExceptionHandler({MemberStatusNotValidException.class})
    public ResponseEntity<ErrorResponse> memberStatusNotValid(final MemberStatusNotValidException exception) {
        final ErrorCode errorCode = exception.getErrorCode();
        logWarn(errorCode, exception.getStatus());
        return createErrorResponse(errorCode);
    }

    @ExceptionHandler({OAuthProviderTypeNotValidException.class})
    public ResponseEntity<ErrorResponse> oauthProviderTypeNotValid(final OAuthProviderTypeNotValidException exception) {
        final ErrorCode errorCode = exception.getErrorCode();
        logWarn(errorCode, exception.getRegistrationId());
        return createErrorResponse(errorCode);
    }

    @ExceptionHandler({PasswordFormatNotValidException.class})
    public ResponseEntity<ErrorResponse> passwordFormatNotValid(final PasswordFormatNotValidException exception) {
        final ErrorCode errorCode = exception.getErrorCode();
        logWarn(errorCode);
        return createErrorResponse(errorCode);
    }

    @ExceptionHandler({PasswordNotMatchedException.class})
    public ResponseEntity<ErrorResponse> passwordNotMatched(final PasswordNotMatchedException exception) {
        final ErrorCode errorCode = exception.getErrorCode();
        logWarn(errorCode);
        return createErrorResponse(errorCode);
    }

    @ExceptionHandler({PhoneNumberFormatNotValidException.class})
    public ResponseEntity<ErrorResponse> phoneNumberFormatNotValid(final PhoneNumberFormatNotValidException exception) {
        final ErrorCode errorCode = exception.getErrorCode();
        logWarn(errorCode, exception.getPhoneNumber());
        return createErrorResponse(errorCode);
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponse> usernameNotFound(final UsernameNotFoundException exception) {
        final ErrorCode errorCode = ErrorCode.INVALID_EMAIL;
        logWarn(errorCode, exception.getMessage());
        return createErrorResponse(errorCode);
    }

    @ExceptionHandler({AccountExpiredException.class})
    public ResponseEntity<ErrorResponse> accountExpired(final AccountExpiredException exception) {
        final ErrorCode errorCode = ErrorCode.EXPIRED_MEMBER;
        logWarn(errorCode, exception.getMessage());
        return createErrorResponse(errorCode);
    }

    @ExceptionHandler({LockedException.class})
    public ResponseEntity<ErrorResponse> locked(final LockedException exception) {
        final ErrorCode errorCode = ErrorCode.LOCKED_MEMBER;
        logWarn(errorCode, exception.getMessage());
        return createErrorResponse(errorCode);
    }

    @ExceptionHandler({CredentialsExpiredException.class})
    public ResponseEntity<ErrorResponse> credentialsExpired(final CredentialsExpiredException exception) {
        final ErrorCode errorCode = ErrorCode.NEED_VERIFY_EMAIL;
        logWarn(errorCode, exception.getMessage());
        return createErrorResponse(errorCode);
    }

    @ExceptionHandler({DisabledException.class})
    public ResponseEntity<ErrorResponse> disabled(final DisabledException exception) {
        final ErrorCode errorCode = ErrorCode.LEAVE_MEMBER;
        logWarn(errorCode, exception.getMessage());
        return createErrorResponse(errorCode);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ErrorResponse> badCredentials(final BadCredentialsException exception) {
        final ErrorCode errorCode = ErrorCode.CHECK_EMAIL_AND_PASSWORD;
        logWarn(errorCode, exception.getMessage());
        return createErrorResponse(errorCode);
    }
}
