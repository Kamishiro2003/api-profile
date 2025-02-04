package com.api.profile.users.domain.model.error;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;

class ErrorCodeTests {

  private ErrorCode errorCode;

  @BeforeEach
  void setUp() {
    errorCode = new ErrorCode();
  }

  @ParameterizedTest
  @DisplayName("Map an ExceptionCode to an appropriate HttpStatus")
  @CsvSource(
      {
          "INVALID_INPUT, BAD_REQUEST",
          "INVALID_ID, BAD_REQUEST",
          "PARAMETER_NOT_PROVIDED, BAD_REQUEST",
          "NOT_FOUND, NOT_FOUND",
          "DUPLICATED_RECORD, CONFLICT"
      }
  )
  void getHttpStatusFromExceptionCode_MapExceptionCodeToHttpStatus_ShouldReturnHttpStatus(
      String exceptionCodeString, String expectedHttpStatusString) {
    // Arrange
    ExceptionCode exceptionCode = ExceptionCode.valueOf(exceptionCodeString);
    HttpStatus expectedHttpStatus = HttpStatus.valueOf(expectedHttpStatusString);
    // Act
    HttpStatus actualHttpStatus = errorCode.getHttpStatusFromExceptionCode(exceptionCode);
    // Assert
    assertEquals(expectedHttpStatus, actualHttpStatus);
  }
}