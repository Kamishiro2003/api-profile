package com.api.profile.users.infrastructure.configuration.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
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
import java.io.PrintWriter;
import java.io.StringWriter;
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
import org.springframework.security.core.context.SecurityContext;
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
  }

  @Test
  void doFilterInternal_NoJwtToken_ShouldNotSetAuthentication()
      throws ServletException, IOException {
    // Arrange
    when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

    // Act
    jwtTokenValidator.doFilterInternal(request, response, filterChain);

    // Assert
    SecurityContext context = SecurityContextHolder.getContext();
    assertNotNull(context);
    assertNull(context.getAuthentication());
    verify(filterChain, times(1)).doFilter(request, response);
  }

  @Test
  void doFilterInternal_InvalidJwtToken_ShouldReturnErrorResponse()
      throws ServletException, IOException {
    // Arrange
    String invalidToken = "Bearer invalid_jwt_token";
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);

    when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(invalidToken);
    when(jwtUtils.validateToken("invalid_jwt_token")).thenThrow(new InvalidTokenException(
        "Invalid token"));
    when(response.getWriter()).thenReturn(printWriter);

    // Act
    jwtTokenValidator.doFilterInternal(request, response, filterChain);

    // Assert
    verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    verify(response).setContentType("application/json");
    printWriter.flush();
    String responseBody = stringWriter.toString();
    assertTrue(responseBody.contains("TOKEN-INVALID"));
    assertTrue(responseBody.contains("Token is invalid"));

    verifyNoInteractions(filterChain);
  }
}