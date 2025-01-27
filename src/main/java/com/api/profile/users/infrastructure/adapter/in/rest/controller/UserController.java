package com.api.profile.users.infrastructure.adapter.in.rest.controller;

import com.api.profile.users.application.port.in.user.UserCreateUseCase;
import com.api.profile.users.application.port.in.user.UserRetrieveUseCase;
import com.api.profile.users.infrastructure.adapter.in.rest.mapper.UserRestMapper;
import com.api.profile.users.infrastructure.adapter.in.rest.model.request.UserCreateRequest;
import com.api.profile.users.infrastructure.adapter.in.rest.model.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing user-related operations.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(
    name = "User Authentication & Management",
    description = "Controller to manage user-related operations"
)
@Slf4j
public class UserController {

  private final UserCreateUseCase createUseCase;

  private final UserRetrieveUseCase retrieveUseCase;

  private final UserRestMapper restMapper;

  /**
   * Create a new user entry.
   *
   * @param createRequest the user creation request
   * @return the created user
   */
  @Operation(summary = "Create a new user")
  @ApiResponses(
      value = {
          @ApiResponse(
              description = "Successfully created a new user",
              responseCode = "201",
              content = {
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(
                          implementation = UserCreateRequest.class
                      )
                  )
              }
          ),
          @ApiResponse(
              description = "An invalid input was provided",
              responseCode = "400",
              content = {
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = UserCreateRequest.class)
                  )
              }
          ),
          @ApiResponse(
              responseCode = "409",
              description = """
                  User with the specified document id already exists
                  User with the specified email already exists
                  """,
              content = {
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = UserCreateRequest.class)
                  )
              }
          ),
          @ApiResponse(
              responseCode = "500",
              description = "Internal Server Error. An unexpected error occurred on the server.",
              content = {
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = UserCreateRequest.class)
                  )
              }
          )
      }
  )
  @PostMapping
  public ResponseEntity<UserResponse> saveOne(@Valid @RequestBody UserCreateRequest createRequest) {

    log.info("Received request to create a new user");
    return new ResponseEntity<>(restMapper.toUserResponse(
        createUseCase.createOne(restMapper.createRequestToDomain(createRequest))),
        HttpStatus.CREATED);
  }

  /**
   * Retrieve a user by document id.
   *
   * @param documentId the user document id
   * @return the retrieved user
   */
  @Operation(summary = "Retrieve user by document id")
  @ApiResponses(
      value = {
          @ApiResponse(
              description = "Successfully retrieved user",
              responseCode = "200",
              content = {
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(
                          implementation = UserResponse.class
                      )
                  )
              }
          ),
          @ApiResponse(
              description = "USER was not found",
              responseCode = "401",
              content = {
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = UserResponse.class)
                  )
              }
          ),
          @ApiResponse(
              responseCode = "500",
              description = "Internal Server Error. An unexpected error occurred on the server.",
              content = {
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = UserResponse.class)
                  )
              }
          )
      }
  )
  @GetMapping("/{documentId}")
  public ResponseEntity<UserResponse> getUserByDocumentId(
      @PathVariable("documentId") String documentId
  ) {

    log.info("Received request to retrieve a user by document id");
    return new ResponseEntity<>(
        restMapper.toUserResponse(retrieveUseCase.findByDocumentId(documentId)), HttpStatus.OK);
  }
}
