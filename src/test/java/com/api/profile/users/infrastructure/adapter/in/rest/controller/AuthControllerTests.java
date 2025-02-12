package com.api.profile.users.infrastructure.adapter.in.rest.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.api.profile.users.application.usecase.user.UserDetailServiceImpl;
import com.api.profile.users.domain.exception.InvalidPasswordException;
import com.api.profile.users.domain.model.auth.AuthLoginModel;
import com.api.profile.users.domain.model.auth.AuthResponseModel;
import com.api.profile.users.domain.model.error.ErrorCode;
import com.api.profile.users.infrastructure.adapter.in.rest.mapper.AuthenticationRestMapper;
import com.api.profile.users.infrastructure.adapter.in.rest.model.request.AuthLoginRequest;
import com.api.profile.users.infrastructure.adapter.in.rest.model.response.AuthResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import utils.AuthUtils;

@ExtendWith(MockitoExtension.class)
class AuthControllerTests {

  @Mock
  private UserDetailServiceImpl detailService;

  @Mock
  private AuthenticationRestMapper mapper;

  @InjectMocks
  private AuthController controller;

  private MockMvc mockMvc;

  private ObjectMapper objectMapper;

  private AuthLoginRequest authLoginRequest;

  private AuthLoginModel authLoginModel;

  private AuthResponse authResponse;

  private AuthResponseModel authResponseModel;

  @BeforeEach
  void setUp() {

    ErrorCode errorCode = new ErrorCode();
    mockMvc = MockMvcBuilders.standaloneSetup(controller)
        .setControllerAdvice(new GlobalControllerAdvice(errorCode))
        .build();
    objectMapper = new ObjectMapper();

    authLoginRequest = AuthUtils.getAuthLoginRequest();
    authLoginModel = AuthUtils.getAuthLoginModel();
    authResponse = AuthUtils.getAuthResponse();
    authResponseModel = AuthUtils.getAuthResponseModel();
  }

  @DisplayName("Login - Successful Authentication")
  @Test
  void login_ShouldReturnAuthResponseWithStatusOk() {
    // Arrange
    when(mapper.authLoginRequestToDomain(authLoginRequest)).thenReturn(authLoginModel);
    when(detailService.loginUser(authLoginModel)).thenReturn(authResponseModel);
    when(mapper.toResponse(authResponseModel)).thenReturn(authResponse);

    // Act
    ResponseEntity<AuthResponse> response = controller.login(authLoginRequest);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(authResponse, response.getBody());
    verify(mapper, times(1)).authLoginRequestToDomain(any(AuthLoginRequest.class));
    verify(detailService, times(1)).loginUser(any());
    verify(mapper, times(1)).toResponse(any(AuthResponseModel.class));
  }

  @DisplayName("Login - Invalid Credentials")
  @Test
  void login_WhenInvalidCredentials_ShouldThrowUnauthorized() {
    // Arrange
    when(mapper.authLoginRequestToDomain(authLoginRequest)).thenReturn(authLoginModel);
    when(detailService.loginUser(authLoginModel)).thenThrow(new InvalidPasswordException());

    // Act & Assert
    assertThrows(
        InvalidPasswordException.class, () -> {
          controller.login(authLoginRequest);
        }
    );
    verify(mapper, times(1)).authLoginRequestToDomain(any(AuthLoginRequest.class));
    verify(detailService, times(1)).loginUser(any(AuthLoginModel.class));
  }

  @DisplayName("Login - Missing document Id or Password")
  @Test
  void login_WhenMissingCredentials_ShouldReturnBadRequest() throws Exception {
    // Arrange
    AuthLoginRequest invalidRequest =
        AuthLoginRequest.builder().documentId(null).password(null).build();
    String requestBody = objectMapper.writeValueAsString(invalidRequest);

    // Act & Assert
    mockMvc.perform(post("/api/v1/auth/log-in").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("USER-INVALID-PARAMETER/S"))
        .andExpect(jsonPath("$.message").value("Invalid user parameter/s in the request"))
        .andExpect(jsonPath("$.details", hasItem("Field documentId cannot be empty or null")))
        .andExpect(jsonPath("$.details", hasItem("Field password cannot be empty or null")));
  }
}