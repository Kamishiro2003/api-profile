package com.api.profile.users.infrastructure.configuration.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.profile.users.domain.exception.InvalidTokenException;
import com.api.profile.users.domain.model.user.RoleEnum;
import com.api.profile.users.util.jwt.JwtUtils;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import utils.UserTestUtils;

@ExtendWith(MockitoExtension.class)
class JwtTokenValidatorTests {

  private static final String TOKEN = "Bearer valid_jwt_token";

  private static final String DOCUMENT_ID = UserTestUtils.getValiduserModel().getDocumentId();

  private static final String ROLE = "ROLE_" + RoleEnum.USER;

  @Mock
  private HttpServletRequest request;

  @Mock
  private JwtUtils jwtUtils;

  @Mock
  private HttpServletResponse response;

  @Mock
  private FilterChain filterChain;

  @Mock
  private DecodedJWT decodedJWT;

  @InjectMocks
  private JwtTokenValidator jwtTokenValidator;

  @BeforeEach
  void setUp() {

    SecurityContextHolder.clearContext();
  }

  @DisplayName("doFilterInternal - Valid JWT Token")
  @Test
  void doFilterInternal_WithValidJwtToken_ShouldSetAuthentication()
      throws ServletException, IOException {
    // Arrange
    when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(TOKEN);
    when(jwtUtils.validateToken("valid_jwt_token")).thenReturn(decodedJWT);
    when(jwtUtils.extractDocumentId(decodedJWT)).thenReturn(DOCUMENT_ID);

    // Mock Claim to avoid lambda issues
    Claim authorityClaim = mock(Claim.class);
    when(authorityClaim.asString()).thenReturn(ROLE);
    when(jwtUtils.getSpecificClaim(decodedJWT, "authorities")).thenReturn(authorityClaim);

    // Act
    jwtTokenValidator.doFilterInternal(request, response, filterChain);

    // Assert
    UsernamePasswordAuthenticationToken authentication =
        (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext()
            .getAuthentication();
    assertNotNull(authentication);
    assertEquals(DOCUMENT_ID, authentication.getPrincipal());

    Collection<SimpleGrantedAuthority> expectedAuthorities =
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    assertEquals(expectedAuthorities, authentication.getAuthorities());

    verify(filterChain, times(1)).doFilter(request, response);
  }

  @DisplayName("doFilterInternal - Invalid JWT Token")
  @Test
  void doFilterInternal_WithInvalidJwtToken_ShouldThrowExceptionAndNotSetAuthentication() {
    // Arrange
    var invalidToken = "invalid_token";
    when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(invalidToken);

    // Act & Assert

    assertThrows(
        InvalidTokenException.class,
        () -> jwtTokenValidator.doFilterInternal(request, response, filterChain)
    );
    verify(request, times(1)).getHeader(HttpHeaders.AUTHORIZATION);
  }

  @DisplayName("doFilterInternal - No JWT Token")
  @Test
  void doFilterInternal_NoJwtToken_ShouldNotSetAuthentication() {
    // Arrange
    when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

    // Act & Assert

    assertThrows(
        InvalidTokenException.class,
        () -> jwtTokenValidator.doFilterInternal(request, response, filterChain)
    );
    verify(request, times(1)).getHeader(HttpHeaders.AUTHORIZATION);
  }
}