package com.api.profile.users.infrastructure.adapter.in.rest.controller;

import com.api.profile.users.application.usecase.user.UserDetailServiceImpl;
import com.api.profile.users.infrastructure.adapter.in.rest.mapper.AuthenticationRestMapper;
import com.api.profile.users.infrastructure.adapter.in.rest.model.request.AuthLoginRequest;
import com.api.profile.users.infrastructure.adapter.in.rest.model.response.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that manages authentication requests for the application. Provides an endpoint
 * for logging in users and generating JWT tokens.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(
    name = "Authentication Management",
    description = "controller that manages authentication requests for the application"
)
public class AuthController {

  private final UserDetailServiceImpl detailService;

  private final AuthenticationRestMapper mapper;

  /**
   * Endpoint for logging in users. Takes user credentials, authenticates the user, and returns a
   * JWT token in the response.
   *
   * @param request the login request containing username and password
   * @return a ResponseEntity with {@link AuthResponse} containing authentication details
   */
  @Operation(summary = "login")
  @ApiResponses(
      value = {
          @ApiResponse(
              description = "User logged successfully",
              responseCode = "200",
              content = {
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(
                          implementation = AuthLoginRequest.class
                      )
                  )
              }
          ),
          @ApiResponse(
              description = "User password is incorrect",
              responseCode = "400",
              content = {
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = AuthLoginRequest.class)
                  )
              }
          ),
          @ApiResponse(
              description = "USER was not found",
              responseCode = "401",
              content = {
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = AuthLoginRequest.class)
                  )
              }
          ),
          @ApiResponse(
              responseCode = "500",
              description = "Internal Server Error. An unexpected error occurred on the server.",
              content = {
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = AuthLoginRequest.class)
                  )
              }
          )
      }
  )
  @PostMapping("/log-in")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthLoginRequest request) {

    return new ResponseEntity<>(
        mapper.toResponse(detailService.loginUser(mapper.authLoginRequestToDomain(request))),
        HttpStatus.OK
    );
  }
}
