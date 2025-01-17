package com.api.profile.users.util.application.adapter;

import com.api.profile.users.application.port.in.user.PasswordHashCreateUseCase;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 * This class provides a concrete implementation for securely hashing passwords using the BCrypt
 * algorithm.
 */
@Component
public class PasswordHashAdapter implements PasswordHashCreateUseCase {

  /**
   * {@inheritDoc}
   */
  @Override
  public String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }
}
