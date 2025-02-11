package com.api.profile.users.application.usecase.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.profile.users.application.port.in.user.JwtUtilsUseCase;
import com.api.profile.users.application.port.in.user.UserRetrieveUseCase;
import com.api.profile.users.domain.exception.InvalidPasswordException;
import com.api.profile.users.domain.model.auth.AuthLoginModel;
import com.api.profile.users.domain.model.auth.AuthResponseModel;
import com.api.profile.users.domain.model.user.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import utils.AuthUtils;
import utils.UserTestUtils;

@ExtendWith(MockitoExtension.class)
class UserDetailServiceImplTests {

  @Mock
  private JwtUtilsUseCase jwtUtilsUseCase;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private UserRetrieveUseCase retrieveUseCase;

  @InjectMocks
  private UserDetailServiceImpl service;

  private String documentId;

  private String password;

  private UserModel userModel;

  private AuthLoginModel authLoginModel;

  @BeforeEach
  void setUp() {

    userModel = UserTestUtils.getValiduserModel();
    authLoginModel = AuthUtils.getAuthLoginModel();
    password = userModel.getPassword();
    documentId = userModel.getDocumentId();
  }

  @DisplayName("load user by username - Valid user")
  @Test
  void loadUserByUsername_WhenUserExists_ShouldReturnUserDetailsWithAuthorities() {
    // Arrange
    when(retrieveUseCase.findByDocumentId(anyString())).thenReturn(userModel);

    // Act
    UserDetails userDetails = service.loadUserByUsername(documentId);

    // Assert
    assertNotNull(userDetails);
    assertEquals(documentId, userDetails.getUsername());
    assertTrue(userDetails.getAuthorities()
        .stream()
        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
    verify(retrieveUseCase, times(1)).findByDocumentId(anyString());
  }

  @DisplayName("Load user by username - Invalid user")
  @Test
  void loadUserByUsername_WhenUserDoesNotExist_ShouldThrowUsernameNotFoundException() {
    // Arrange
    when(retrieveUseCase.findByDocumentId(anyString())).thenThrow(new UsernameNotFoundException(
        "User not found"));

    // Act & Assert
    UsernameNotFoundException exception = assertThrows(
        UsernameNotFoundException.class, () -> {
          service.loadUserByUsername(documentId);
        }
    );

    assertEquals("User not found", exception.getMessage());
    verify(retrieveUseCase, times(1)).findByDocumentId(anyString());
  }

  @DisplayName("Login user - Valid credentials are provided")
  @Test
  void loginUser_WhenValidCredentialsAreProvided_ShouldReturnAuthResponseModelWithToken() {
    // Arrange
    when(retrieveUseCase.findByDocumentId(documentId)).thenReturn(userModel);
    when(passwordEncoder.matches(password, password)).thenReturn(true);
    when(jwtUtilsUseCase.createToken(any(Authentication.class))).thenReturn("generatedToken");

    // Act
    AuthResponseModel response = service.loginUser(authLoginModel);

    // Assert
    assertNotNull(response);
    assertEquals(documentId, response.getDocumentId());
    assertEquals("generatedToken", response.getJwt());
    assertTrue(response.getStatus());
    assertEquals("User logged successfully", response.getMessage());

    // Verify interactions
    verify(retrieveUseCase, times(1)).findByDocumentId(documentId);
    verify(passwordEncoder, times(1)).matches(password, password);
    verify(jwtUtilsUseCase, times(1)).createToken(any(Authentication.class));
  }

  @DisplayName("Authenticate user - Valid password is provided")
  @Test
  void authenticate_WhenValidPasswordIsProvided_ShouldReturnAuthenticationObject() {
    // Arrange
    when(retrieveUseCase.findByDocumentId(anyString())).thenReturn(userModel);
    when(passwordEncoder.matches(password, password)).thenReturn(true);

    // Act
    Authentication authentication = service.authenticate(documentId, password);

    // Assert
    assertNotNull(authentication);
    assertEquals(documentId, authentication.getName());
    assertEquals(password, authentication.getCredentials());
    assertTrue(authentication.getAuthorities()
        .stream()
        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
    verify(retrieveUseCase, times(1)).findByDocumentId(anyString());
    verify(passwordEncoder, times(1)).matches(password, password);
  }

  @DisplayName("Authenticate user - Invalid password is provided")
  @Test
  void authenticate_WhenInvalidPasswordIsProvided_ShouldThrowInvalidPasswordException() {
    // Arrange
    when(retrieveUseCase.findByDocumentId(anyString())).thenReturn(userModel);
    when(passwordEncoder.matches(password, password)).thenReturn(false);

    // Act & Assert
    InvalidPasswordException exception = assertThrows(
        InvalidPasswordException.class, () -> {
          service.authenticate(documentId, password);
        }
    );

    assertNotNull(exception);
    verify(retrieveUseCase, times(1)).findByDocumentId(anyString());
    verify(passwordEncoder, times(1)).matches(password, password);
  }
}