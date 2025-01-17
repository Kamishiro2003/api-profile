package com.api.profile.users.infrastructure.adapter.in.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.api.profile.users.domain.exception.ApplicationException;
import com.api.profile.users.domain.model.error.ErrorCode;
import com.api.profile.users.domain.model.error.ErrorModel;
import com.api.profile.users.domain.model.error.ExceptionCode;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import utils.TestObject;

@ExtendWith(MockitoExtension.class)
class GlobalControllerAdviceTests {
  @Mock
  private ErrorCode errorCode;

  @InjectMocks
  private GlobalControllerAdvice controllerAdvice;

  private MockHttpServletRequest request;

  @BeforeEach
  void setUp() {
    request = new MockHttpServletRequest();
    request.setRequestURI("/test/path");
  }

  @Test
  @DisplayName("Handle ApplicationException - should return correct ErrorModel and status")
  void handlerApplicationException_ShouldReturnErrorModel() {
    // Arrange
    ApplicationException
        exception = new ApplicationException("Test message", ExceptionCode.NOT_FOUND, "E-TEST");
    when(errorCode.getHttpStatusFromExceptionCode(ExceptionCode.NOT_FOUND)).thenReturn(HttpStatus.NOT_FOUND);

    // Act
    ResponseEntity<ErrorModel>
        response = controllerAdvice.handlerApplicationException(request, exception);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    ErrorModel errorModel = response.getBody();
    assertNotNull(errorModel);
    assertEquals("E-TEST", errorModel.getCode());
    assertEquals("Test message", errorModel.getMessage());
    assertEquals("/test/path", errorModel.getPath());
  }

  @Test
  @DisplayName("Handle MethodArgumentNotValidException - should return correct ErrorModel and status")
  void handlerMethodArgumentNotValidException_ShouldReturnErrorModel() {
    // Arrange
    TestObject testObject = new TestObject();
    BindingResult bindingResult = new BeanPropertyBindingResult(testObject, "testObject");
    bindingResult.rejectValue("field1", "code1", "Field1 is invalid");
    bindingResult.rejectValue("field2", "code2", "Field2 is required");

    MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

    when(errorCode.getHttpStatusFromExceptionCode(ExceptionCode.INVALID_ID)).thenReturn(HttpStatus.BAD_REQUEST);

    // Act
    ResponseEntity<ErrorModel> response = controllerAdvice.handlerMethodArgumentNotValidException(request, exception);

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    ErrorModel errorModel = response.getBody();
    assertNotNull(errorModel);
    assertEquals("USER-INVALID-PARAMETER/S", errorModel.getCode());
    assertEquals("Invalid user parameter/s in the request", errorModel.getMessage());
    assertEquals("/test/path", errorModel.getPath());
    assertEquals(List.of("Field1 is invalid", "Field2 is required"), errorModel.getDetails());
  }

  @Test
  @DisplayName("Handle generic Exception - should return correct ErrorModel and status")
  void handlerGenericError_ShouldReturnErrorModel() {
    // Arrange
    Exception exception = new Exception("Unexpected error occurred");

    // Act
    ResponseEntity<ErrorModel> response = controllerAdvice.handlerGenericError(request, exception);

    // Assert
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    ErrorModel errorModel = response.getBody();
    assertNotNull(errorModel);
    assertEquals("E-UNKNOWN-ERROR", errorModel.getCode());
    assertEquals("Unexpected error occurred", errorModel.getMessage());
    assertEquals("/test/path", errorModel.getPath());
  }
}