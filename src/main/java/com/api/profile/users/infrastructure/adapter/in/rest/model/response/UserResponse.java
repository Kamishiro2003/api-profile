package com.api.profile.users.infrastructure.adapter.in.rest.model.response;

import com.api.profile.users.domain.model.user.DocumentType;
import com.api.profile.users.domain.model.user.RoleEnum;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the response model for user data in the REST API.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserResponse {

  private String name;

  private String lastName;

  private DocumentType documentType;

  private String documentId;

  private String phoneNumber;

  private String address;

  private String email;

  private RoleEnum role;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private Boolean isActive;
}
