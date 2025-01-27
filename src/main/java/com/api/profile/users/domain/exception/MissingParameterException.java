package com.api.profile.users.domain.exception;

import com.api.profile.users.domain.model.error.ExceptionCode;

/**
 * Exception thrown when required parameters are missing.
 */
public class MissingParameterException extends ApplicationException {

  /**
   * Constructs a new exception for a single missing parameter.
   *
   * @param parameter the name of the missing parameter
   */
  public MissingParameterException(String parameter) {

    super("The parameter: " + parameter + " is missing.", ExceptionCode.INVALID_INPUT,
        "MISSING-PARAMETER");
  }

}
