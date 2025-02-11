package com.api.profile.users.domain.exception;

import com.api.profile.users.domain.model.error.ExceptionCode;

/**
 * Thrown when an invalid password is provided.
 * Sets a default message, exception code, and error identifier.
 */
public class InvalidPasswordException extends ApplicationException {

  /**
   * Constructs the InvalidPasswordException with a default message, exception code, and identifier.
   */
  public InvalidPasswordException() {
    super("password provided is incorrect", ExceptionCode.INVALID_PASSWORD, "INVALID-PASSWORD");
  }
}
