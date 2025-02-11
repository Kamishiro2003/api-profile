package com.api.profile.users.util.application.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.profile.users.util.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class JwtUtilsAdapterTests {

  @Mock
  private JwtUtils jwtUtils;

  @InjectMocks
  private JwtUtilsAdapter adapter;

  private Authentication authentication;

  @BeforeEach
  void setUp() {
    // Mock Authentication object
    authentication = mock(Authentication.class);
  }

  @DisplayName("createToken - When valid authentication is provided - Should return a JWT token")
  @Test
  void createToken_WhenValidAuthenticationIsProvided_ShouldReturnJwtToken() {
    // Arrange
    String expectedToken = "jwtToken";
    when(jwtUtils.createToken(authentication)).thenReturn(expectedToken);

    // Act
    String actualToken = adapter.createToken(authentication);

    // Assert
    assertNotNull(actualToken);
    assertEquals(expectedToken, actualToken);

    // Verify interaction
    verify(jwtUtils, times(1)).createToken(authentication);
  }
}