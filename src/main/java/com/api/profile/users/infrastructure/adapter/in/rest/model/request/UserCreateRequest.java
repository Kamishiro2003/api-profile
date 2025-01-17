package com.api.profile.users.infrastructure.adapter.in.rest.model.request;

import com.api.profile.users.domain.model.user.DocumentType;
import com.api.profile.users.domain.model.user.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a request to create new user.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserCreateRequest {

  @NotBlank(message = "Field name cannot be empty or null")
  @Size(
      max = 150,
      message = "Field name must not exceed 150 characters"
  )
  private String name;

  @NotBlank(message = "Field lastName cannot be empty or null")
  @Size(
      max = 150,
      message = "Field lastName must not exceed 150 characters"
  )
  private String lastName;

  @NotNull(message = "Field documentType cannot be null")
  private DocumentType documentType;

  @NotBlank(message = "Field documentId cannot be empty or null")
  @Size(
      max = 30,
      message = "Field documentId must not exceed 30 characters"
  )
  private String documentId;

  @NotBlank(message = "Field phoneNumber cannot be empty or null")
  @Size(
      max = 15,
      message = "Field phoneNumber must not exceed 15 characters"
  )
  private String phoneNumber;

  @NotBlank(message = "Field address cannot be empty or null")
  @Size(
      max = 200,
      message = "Field address must not exceed 200 characters"
  )
  private String address;

  @NotBlank(message = "Field lastName cannot be empty or null")
  @Size(
      max = 200,
      message = "Field lastName must not exceed 200 characters"
  )
  private String email;

  @NotBlank(message = "Field lastName cannot be empty or null")
  @Size(
      max = 200,
      message = "Field lastName must not exceed 150 characters"
  )
  private String password;

  @NotNull(message = "Field role cannot be null")
  private RoleEnum role;
}
