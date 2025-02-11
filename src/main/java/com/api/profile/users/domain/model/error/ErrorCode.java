package com.api.profile.users.domain.model.error;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * This class maps general exception codes to HTTP status codes.
 */
@Component
public class ErrorCode {

  /**
   * Map an {@link ExceptionCode} to an appropriate {@link HttpStatus} response code.
   *
   * @param exceptionCode the {@link ExceptionCode} representing the specific error encountered
   * @return the corresponding {@link HttpStatus} to use in the HTTP response
   */
  public HttpStatus getHttpStatusFromExceptionCode(ExceptionCode exceptionCode) {

    return switch (exceptionCode) {
      case INVALID_INPUT, INVALID_ID, PARAMETER_NOT_PROVIDED, INVALID_PASSWORD ->
          HttpStatus.BAD_REQUEST;
      case NOT_FOUND -> HttpStatus.NOT_FOUND;
      case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
      case DUPLICATED_RECORD -> HttpStatus.CONFLICT;
    };
  }
}
