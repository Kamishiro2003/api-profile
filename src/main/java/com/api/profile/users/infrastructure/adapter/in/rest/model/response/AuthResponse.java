package com.api.profile.users.infrastructure.adapter.in.rest.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an authentication response containing user details and JWT.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AuthResponse {
  private String documentId;
  private String message;
  private String jwt;
  private Boolean status;
}
