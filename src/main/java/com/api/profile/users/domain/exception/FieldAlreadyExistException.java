package com.api.profile.users.domain.exception;

import com.api.profile.users.domain.model.error.ExceptionCode;

/**
 * Exception throw when a field of the instance already exist.
 */
public class FieldAlreadyExistException extends ApplicationException {

  /**
   * Exception thrown when a field in a record already exists and violates uniqueness constraints.
   *
   * @param message   the detail message explaining the cause of the exception.
   * @param errorCode the error code to be used for this exception.
   */
  public FieldAlreadyExistException(String message, String errorCode) {
    super(message, ExceptionCode.DUPLICATED_RECORD, errorCode);
  }
}
