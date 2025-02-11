package com.api.profile.users.util.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import com.api.profile.users.domain.exception.InvalidTokenException;
import com.api.profile.users.domain.model.user.RoleEnum;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;
import utils.UserTestUtils;

@ExtendWith(MockitoExtension.class)
class JwtUtilsTests {

  private static final String DOCUMENT_ID = UserTestUtils.getValiduserModel().getDocumentId();

  private static final String ROLE = "ROLE_" + RoleEnum.USER;

  private static final String PRIVATE_KEY = "privateKey";

  private static final String USER_GENERATOR = "userGenerator";

  @Mock
  private Authentication authentication;

  @InjectMocks
  private JwtUtils jwtUtils;

  @BeforeEach
  void setUp() {

    ReflectionTestUtils.setField(jwtUtils, "privateKey", PRIVATE_KEY);
    ReflectionTestUtils.setField(jwtUtils, "userGenerator", USER_GENERATOR);
  }

  @DisplayName("Create token - Successful token creation")
  @Test
  void createToken_WhenAuthenticationIsProvided_ShouldReturnValidJwtToken() {
    // Arrange
    Collection<GrantedAuthority> authorities =
        Collections.singletonList(new SimpleGrantedAuthority(ROLE));
    doReturn(DOCUMENT_ID).when(authentication).getPrincipal();
    doReturn(authorities).when(authentication).getAuthorities();

    // Act
    String token = jwtUtils.createToken(authentication);

    // Assert
    assertNotNull(token);
    assertFalse(token.isEmpty());
  }

  @DisplayName("Validate token - Valid token")
  @Test
  void validateToken_WhenTokenIsValid_ShouldReturnDecodedJWT() {
    // Arrange
    Algorithm algorithm = Algorithm.HMAC256(PRIVATE_KEY);
    String token = JWT.create()
        .withIssuer(USER_GENERATOR)
        .withSubject(DOCUMENT_ID)
        .withClaim("authorities", ROLE)
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
        .withJWTId(UUID.randomUUID().toString())
        .withNotBefore(new Date())
        .sign(algorithm);

    // Act
    DecodedJWT decodedJWT = jwtUtils.validateToken(token);

    // Assert
    assertNotNull(decodedJWT);
    assertEquals(DOCUMENT_ID, decodedJWT.getSubject());
  }

  @DisplayName("Validate token - Invalid token")
  @Test
  void validateToken_WhenTokenIsInvalid_ShouldThrowInvalidTokenException() {
    // Arrange
    var invalidToken = "invalidToken";

    // Act && Assert
    assertThrows(InvalidTokenException.class, () -> jwtUtils.validateToken(invalidToken));
  }

  @DisplayName("Extract Document Id - Successful Extraction")
  @Test
  void extractDocumentId_WhenDecodedJWTIsProvided_ShouldReturnDocumentId() {
    // Arrange
    Algorithm algorithm = Algorithm.HMAC256(PRIVATE_KEY);
    String token = JWT.create()
        .withIssuer(USER_GENERATOR)
        .withSubject(DOCUMENT_ID)
        .withIssuedAt(new Date())
        .sign(algorithm);
    DecodedJWT decodedJWT = jwtUtils.validateToken(token);

    // Act
    String extractedUsername = jwtUtils.extractDocumentId(decodedJWT);

    // Assert
    assertEquals(DOCUMENT_ID, extractedUsername);
  }


  @DisplayName("Get Specific Claim - Should Return Claim Value")
  @Test
  void getSpecificClaim_ShouldReturnClaimValue() {
    // Arrange
    Algorithm algorithm = Algorithm.HMAC256(PRIVATE_KEY);
    String token = JWT.create()
        .withIssuer(USER_GENERATOR)
        .withSubject(DOCUMENT_ID)
        .withClaim("authorities", ROLE)
        .withIssuedAt(new Date())
        .sign(algorithm);
    DecodedJWT decodedJWT = jwtUtils.validateToken(token);

    // Act
    String claimValue = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();

    // Assert
    assertEquals(ROLE, claimValue);
  }
}