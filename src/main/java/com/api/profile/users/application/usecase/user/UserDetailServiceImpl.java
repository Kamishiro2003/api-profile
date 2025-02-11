package com.api.profile.users.application.usecase.user;

import com.api.profile.users.application.port.in.user.JwtUtilsUseCase;
import com.api.profile.users.application.port.in.user.UserRetrieveUseCase;
import com.api.profile.users.domain.exception.InvalidPasswordException;
import com.api.profile.users.domain.model.auth.AuthLoginModel;
import com.api.profile.users.domain.model.auth.AuthResponseModel;
import com.api.profile.users.domain.model.user.UserModel;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for managing user authentication and authorization. Implements {@link UserDetailsService}
 * for loading user details by username and handling login logic.
 */
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

  private final JwtUtilsUseCase jwtUtilsUseCase;

  private final PasswordEncoder passwordEncoder;

  private final UserRetrieveUseCase retrieveUseCase;

  /**
   * Loads a user by their document id, including their authorities.
   *
   * @param documentId the document id to load
   * @return a {@link UserDetails} object with user information and authorities
   * @throws UsernameNotFoundException if the user is not found
   */
  @Override
  public UserDetails loadUserByUsername(String documentId) throws UsernameNotFoundException {

    UserModel userModel = retrieveUseCase.findByDocumentId(documentId);
    SimpleGrantedAuthority authority =
        new SimpleGrantedAuthority("ROLE_".concat(userModel.getRole().name()));

    return new User(
        userModel.getDocumentId(),
        userModel.getPassword(),
        true,
        true,
        true,
        true,
        Collections.singletonList(authority)
    );
  }

  /**
   * Authenticates a user and generates an access token.
   *
   * @param authLoginModel the login model containing document id and password
   * @return an {@link AuthResponseModel} with the login status and access token
   */
  public AuthResponseModel loginUser(AuthLoginModel authLoginModel) {

    String documentId = authLoginModel.getDocumentId();
    String password = authLoginModel.getPassword();

    Authentication authentication = this.authenticate(documentId, password);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String accessToken = jwtUtilsUseCase.createToken(authentication);
    return AuthResponseModel.builder()
        .documentId(documentId)
        .message("User logged successfully")
        .jwt(accessToken)
        .status(true)
        .build();
  }

  /**
   * Authenticates a user by verifying the provided password.
   *
   * @param documentId the document id of the user
   * @param password   the plain-text password to verify
   * @return an {@link Authentication} object upon successful authentication
   * @throws InvalidPasswordException if the password is incorrect
   */
  public Authentication authenticate(String documentId, String password) {

    UserDetails userDetails = this.loadUserByUsername(documentId);

    if (!passwordEncoder.matches(password, userDetails.getPassword())) {
      throw new InvalidPasswordException();
    }

    return new UsernamePasswordAuthenticationToken(
        documentId,
        password,
        userDetails.getAuthorities()
    );
  }
}
