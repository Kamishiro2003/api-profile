package com.api.profile.users.infrastructure.adapter.in.rest.mapper;


import com.api.profile.users.domain.model.auth.AuthLoginModel;
import com.api.profile.users.domain.model.auth.AuthResponseModel;
import com.api.profile.users.infrastructure.adapter.in.rest.model.request.AuthLoginRequest;
import com.api.profile.users.infrastructure.adapter.in.rest.model.response.AuthResponse;
import org.springframework.stereotype.Component;

/**
 * Adapter for converting authentication-related request and response objects
 * between domain models and API models.
 */
@Component
public class AuthenticationRestMapper {

  /**
   * Converts an {@link AuthLoginRequest} to an {@link AuthLoginModel}.
   *
   * @param request the authentication request from the client
   * @return the domain model for login credentials
   */
  public AuthLoginModel authLoginRequestToDomain(AuthLoginRequest request) {

    return AuthLoginModel.builder()
        .documentId(request.getDocumentId())
        .password(request.getPassword())
        .build();
  }

  /**
   * Converts an {@link AuthResponseModel} to an {@link AuthResponse}.
   *
   * @param response the domain model with authentication response data
   * @return the API response model with authentication details
   */
  public AuthResponse toResponse(AuthResponseModel response) {

    return AuthResponse.builder()
        .documentId(response.getDocumentId())
        .message(response.getMessage())
        .jwt(response.getJwt())
        .status(response.getStatus())
        .build();
  }
}
