package com.api.profile.users.application.port.in.user;

/**
 * Use case for securely hashing passwords.
 */
public interface PasswordHashCreateUseCase {

  /**
   * Hash the given plain-text password using a secure algorithm.
   *
   * @param password the plain-text password to be hashed.
   * @return a hashed representation of the password.
   */
  String hashPassword(String password);
}
