package com.api.profile.users.infrastructure.adapter.in.rest.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.AuthUtils;

class AuthenticationRestMapperTests {

  private AuthenticationRestMapper mapper;

  @BeforeEach
  void setUp() {

    mapper = new AuthenticationRestMapper();
  }

  @DisplayName("Convert AuthLoginRequest to AuthLoginModel")
  @Test
  void authLoginRequestToDomain_WhenAuthLoginRequestIsProvided_ShouldReturnAuthLoginModel() {
    // Arrange
    var authLoginRequest = AuthUtils.getAuthLoginRequest();

    // Act
    var result = mapper.authLoginRequestToDomain(authLoginRequest);

    // Assert
    assertNotNull(result);
    assertEquals(result.getDocumentId(), authLoginRequest.getDocumentId());
    assertEquals(result.getPassword(), authLoginRequest.getPassword());
  }

  @DisplayName("Convert AuthResponseModel to AuthResponse")
  @Test
  void toResponse_WhenAuthResponseModelIsProvided_ShouldReturnAuthResponse() {
    // Arrange
    var authResponseModel = AuthUtils.getAuthResponseModel();

    // Act
    var result = mapper.toResponse(authResponseModel);

    // Assert
    assertNotNull(result);
    assertEquals(authResponseModel.getDocumentId(), result.getDocumentId());
    assertEquals(authResponseModel.getJwt(), result.getJwt());
    assertEquals(authResponseModel.getStatus(), result.getStatus());
  }
}