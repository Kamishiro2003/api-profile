package com.api.profile.users.util.application.adapter;

import com.api.profile.users.application.port.in.user.JwtUtilsUseCase;
import com.api.profile.users.util.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Adapter class that implements the {@link JwtUtilsUseCase} interface.
 * Delegates the creation of JWT tokens to the {@link JwtUtils} utility class.
 */
@RequiredArgsConstructor
@Component
public class JwtUtilsAdapter implements JwtUtilsUseCase {

  private final JwtUtils jwtUtils;

  /**
   * {@inheritDoc}
   * */
  @Override
  public String createToken(Authentication authentication) {

    return jwtUtils.createToken(authentication);
  }
}
