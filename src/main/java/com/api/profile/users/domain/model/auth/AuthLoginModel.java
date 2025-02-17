package com.api.profile.users.domain.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model representing login credentials with document id and password. Includes Lombok annotations
 * for getters, setters, no-args and all-args constructors, and a builder for flexible object
 * creation.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AuthLoginModel {

  private String documentId;

  private String password;
}
