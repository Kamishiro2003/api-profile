package com.api.profile.users.util.jwt;

import com.api.profile.users.domain.exception.InvalidTokenException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * Utility class for handling JWT creation and related security operations.
 */
@Component
public class JwtUtils {

  @Value("${security.jwt.key.private}")
  private String privateKey;

  @Value("${security.jwt.user.generator}")
  private String userGenerator;

  /**
   * Creates a JWT token for the specified authenticated user.
   *
   * @param authentication the authenticated user context containing principal and authorities
   * @return a signed JWT token with user information and expiration
   */
  public String createToken(Authentication authentication) {

    Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

    String documentId = authentication.getPrincipal().toString();
    String authorities = authentication.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    return JWT.create()
        .withIssuer(this.userGenerator)
        .withSubject(documentId)
        .withClaim("authorities", authorities)
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
        .withJWTId(UUID.randomUUID().toString())
        .withNotBefore(new Date(System.currentTimeMillis()))
        .sign(algorithm);
  }

  /**
   * Validates the provided JWT token by verifying its signature and issuer.
   *
   * @param token the JWT token to be validated
   * @return a {@link DecodedJWT} instance if the token is valid
   * @throws JWTVerificationException if the token is invalid or the verification fails
   */
  public DecodedJWT validateToken(String token) {

    try {
      Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
      JWTVerifier verifier = JWT.require(algorithm).withIssuer(this.userGenerator).build();
      return verifier.verify(token);
    } catch (JWTVerificationException exception) {
      throw new InvalidTokenException(exception.toString());
    }
  }

  /**
   * Extracts the document id (subject) from the provided decoded JWT.
   *
   * @param decodedJwt the decoded JWT from which to extract the subject
   * @return the document id contained in the JWTs subject claim
   */
  public String extractDocumentId(DecodedJWT decodedJwt) {

    return decodedJwt.getSubject();
  }

  /**
   * Retrieves a specific claim from the provided decoded JWT.
   *
   * @param decodedJwt the decoded JWT from which to retrieve the claim
   * @param claimName  the name of the claim to retrieve
   * @return the {@link Claim} corresponding to the specified claim name
   */
  public Claim getSpecificClaim(DecodedJWT decodedJwt, String claimName) {

    return decodedJwt.getClaim(claimName);
  }
}
