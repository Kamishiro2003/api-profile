package com.api.profile.users.domain.exception;

import com.api.profile.users.domain.model.error.ExceptionCode;

/**
 * Thrown when an invalid token is provided. Sets a default message, exception code, and error
 * identifier.
 */
public class InvalidTokenException extends ApplicationException {

  /**
   * Constructs the InvalidTokenException with a parameter, default message, exception code, and
   * identifier.
   */
  public InvalidTokenException(String msg) {
    super("Token is invalid\n " + msg, ExceptionCode.UNAUTHORIZED, "TOKEN-INVALID");
  }
}
