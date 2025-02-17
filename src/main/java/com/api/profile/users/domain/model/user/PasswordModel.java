package com.api.profile.users.domain.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represent a password model that contain details for changing user password.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PasswordModel {

  private String documentId;

  private String oldPassword;

  private String newPassword;

  private String confirmPassword;
}
