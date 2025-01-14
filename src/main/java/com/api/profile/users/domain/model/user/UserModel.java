package com.api.profile.users.domain.model.user;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a user with essential account information.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserModel {

  private String id;

  private String name;

  private String lastName;

  private DocumentType documentType;

  private String documentId;

  private String phoneNumber;

  private String address;

  private String email;

  private String password;

  private RoleEnum role;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private Boolean isActive;
}
