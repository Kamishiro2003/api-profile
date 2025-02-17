package com.api.profile.users.infrastructure.adapter.in.rest.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represent a password request that contain details for changing user password.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PasswordUpdateRequest {
  @NotBlank(message = "Field oldPassword cannot be empty or null")
  @Size(
      max = 80,
      message = "Field oldPassword must not exceed 80 characters"
  )
  private String oldPassword;

  @NotBlank(message = "Field newPassword cannot be empty or null")
  @Size(
      max = 80,
      message = "Field newPassword must not exceed 80 characters"
  )
  private String newPassword;

  @NotBlank(message = "Field confirmPassword cannot be empty or null")
  @Size(
      max = 80,
      message = "Field confirmPassword must not exceed 80 characters"
  )
  private String confirmPassword;
}
