package com.api.profile.users.domain.exception;

import com.api.profile.users.domain.model.error.ExceptionCode;

/**
 * Thrown when an invalid password is provided. Sets a default message, exception code, and error
 * identifier.
 */
public class InvalidPasswordException extends ApplicationException {

  /**
   * Constructs the InvalidPasswordException with a default message, exception code, and
   * identifier.
   */
  public InvalidPasswordException() {

    super("password provided is incorrect", ExceptionCode.INVALID_PASSWORD, "INVALID-PASSWORD");
  }

  /**
   * Constructs the InvalidPasswordException with two fields, exception code, and
   * identifier.
   */
  public InvalidPasswordException(String field, String field2) {
    super(
        "The " + field + " is different to " + field2,
        ExceptionCode.INVALID_PASSWORD,
        "DIFFERENT-PASSWORD-PROVIDED"
    );
  }
}
