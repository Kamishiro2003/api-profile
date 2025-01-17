package com.api.profile.users.domain.exception;

import com.api.profile.users.domain.model.error.ExceptionCode;

/**
 * Exception throw when an instance was not found.
 */
public class InstanceNotFoundException extends ApplicationException {

  /**
   * Exception thrown when an instance of a resource could not be found.
   *
   * @param message   the detail message explaining the cause of the exception.
   * @param errorCode the error code to be used for this exception.
   */
  public InstanceNotFoundException(String message, String errorCode) {
    super(message, ExceptionCode.NOT_FOUND, errorCode);
  }
}
