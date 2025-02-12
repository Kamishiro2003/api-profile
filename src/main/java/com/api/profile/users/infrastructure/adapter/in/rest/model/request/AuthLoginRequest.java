package com.api.profile.users.infrastructure.adapter.in.rest.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an authentication login request with validation constraints.
 * Includes fields for documentId and password.
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AuthLoginRequest {

  @NotBlank(message = "Field documentId cannot be empty or null")
  @Size(max = 20, message = "Field documentId must not exceed 20 characters")
  private String documentId;

  @NotBlank(message = "Field password cannot be empty or null")
  @Size(max = 100, message = "Field password must not exceed 100 characters")
  private String password;
}
