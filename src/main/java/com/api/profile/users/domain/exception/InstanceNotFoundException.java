package com.api.profile.users.domain.exception;

import com.api.profile.users.domain.model.error.ExceptionCode;

/**
 * Exception throw when an instance was not found.
 */
public class InstanceNotFoundException extends ApplicationException {

  /**
   * Exception thrown when an instance of a resource could not be found.
   *
   * @param instance the name of the instance where cause the exception.
   */
  public InstanceNotFoundException(String instance) {

    super(instance + " was not found", ExceptionCode.NOT_FOUND, instance + "-NOT-FOUND");
  }
}
