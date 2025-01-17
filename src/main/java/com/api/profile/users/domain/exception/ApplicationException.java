package com.api.profile.users.domain.exception;

import com.api.profile.users.domain.model.error.ExceptionCode;
import lombok.Getter;

/**
 * A common exception class for all exceptions in the application.
 */
@Getter
public class ApplicationException extends RuntimeException {

  private final ExceptionCode exceptionCode;

  private final String fullErrorCode;

  /**
   * Constructor for ApplicationException.
   *
   * @param message       the message to be displayed for this exception
   * @param exceptionCode the exception code to be used for this exception
   * @param errorCode     the error code to be used for this exception. The error code is a unique
   *                      identifier for the exception thrown. it has the format: identifier,
   *                      example: USER-NOT-FOUND
   */
  public ApplicationException(String message, ExceptionCode exceptionCode, String errorCode) {
    super(message);
    this.exceptionCode = exceptionCode;
    this.fullErrorCode = errorCode;
  }
}
