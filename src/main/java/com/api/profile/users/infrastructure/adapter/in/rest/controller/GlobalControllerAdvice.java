package com.api.profile.users.infrastructure.adapter.in.rest.controller;

import com.api.profile.users.domain.exception.ApplicationException;
import com.api.profile.users.domain.model.error.ErrorCode;
import com.api.profile.users.domain.model.error.ErrorModel;
import com.api.profile.users.domain.model.error.ExceptionCode;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the application.
 */
@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalControllerAdvice {

  private final ErrorCode errorCode;

  /**
   * Handles {@link ApplicationException} by building a standardized {@link ErrorModel} response.
   *
   * @param req the HTTP request that triggered the exception
   * @param ex  the application-specific exception with error details
   * @return a {@link ResponseEntity} with {@link ErrorModel} details
   */
  @ExceptionHandler(value = ApplicationException.class)
  public ResponseEntity<ErrorModel> handlerApplicationException(HttpServletRequest req,
      ApplicationException ex
  ) {

    HttpStatus status = errorCode.getHttpStatusFromExceptionCode(ex.getExceptionCode());
    String message = ex.getMessage();
    String errorCodeUnique = ex.getFullErrorCode();
    String path = req.getRequestURI();

    ErrorModel errorModel = new ErrorModel(errorCodeUnique, message, path);

    log.debug("an application exception occurred with code: {} and message: {}",
        ex.getFullErrorCode(), ex.getMessage());
    return new ResponseEntity<>(errorModel, status);
  }

  /**
   * Handles {@link MethodArgumentNotValidException} for invalid method arguments in request bodies.
   * Builds an {@link ErrorModel} response with validation error details.
   *
   * @param req the HTTP request where the exception occurred
   * @param ex  the exception containing validation errors
   * @return a {@link ResponseEntity} with {@link ErrorModel} details
   */
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorModel> handlerMethodArgumentNotValidException(HttpServletRequest req,
      MethodArgumentNotValidException ex
  ) {

    HttpStatus status = errorCode.getHttpStatusFromExceptionCode(ExceptionCode.INVALID_ID);
    String path = req.getRequestURI();
    String message = "Invalid user parameter/s in the request";
    String errorCodeUnique = "USER-INVALID-PARAMETER/S";
    BindingResult result = ex.getBindingResult();
    List<String> details = result.getFieldErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .toList();
    ErrorModel errorModel = new ErrorModel(errorCodeUnique, message, path, details);
    return new ResponseEntity<>(errorModel, status);
  }

  /**
   * Handles generic exceptions and returns a 500 response.
   *
   * @param exception the exception
   * @return the error response with internal server error status
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorModel> handlerGenericError(HttpServletRequest req,
      Exception exception
  ) {

    String path = req.getRequestURI();

    ErrorModel apiErrorModel =
        new ErrorModel("E-UNKNOWN-ERROR", exception.getMessage(), path, exception);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorModel);
  }
}
