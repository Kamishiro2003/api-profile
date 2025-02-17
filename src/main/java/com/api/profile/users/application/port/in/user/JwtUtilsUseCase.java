package com.api.profile.users.application.port.in.user;

import org.springframework.security.core.Authentication;

/**
 * Interface defining the contract for generating JWT tokens.
 */
public interface JwtUtilsUseCase {

  /**
   * Generates a JWT token based on the provided authentication details.
   *
   * @param authentication Authentication object containing user details
   * @return Generated JWT token as a string
   */
  String createToken(Authentication authentication);
}
