package com.api.profile.users.infrastructure.adapter.in.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.profile.users.application.port.in.user.UserCreateUseCase;
import com.api.profile.users.application.port.in.user.UserRetrieveUseCase;
import com.api.profile.users.application.port.in.user.UserUpdateUseCase;
import com.api.profile.users.domain.model.user.UserModel;
import com.api.profile.users.infrastructure.adapter.in.rest.mapper.UserRestMapper;
import com.api.profile.users.infrastructure.adapter.in.rest.model.request.UserCreateRequest;
import com.api.profile.users.infrastructure.adapter.in.rest.model.request.UserUpdateRequest;
import com.api.profile.users.infrastructure.adapter.in.rest.model.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import utils.UserTestUtils;

@ExtendWith(MockitoExtension.class)
class UserControllerTests {

  @Mock
  private UserCreateUseCase createUseCase;

  @Mock
  private UserRetrieveUseCase retrieveUseCase;

  @Mock
  private UserUpdateUseCase updateUseCase;

  @Mock
  private UserRestMapper mapper;

  @InjectMocks
  private UserController userController;

  private UserCreateRequest createRequest;

  private UserUpdateRequest updateRequest;

  private String documentId;

  private UserModel userModel;

  private UserResponse userResponse;

  @BeforeEach
  void setUp() {
    userModel = UserTestUtils.getValiduserModel();
    documentId = userModel.getDocumentId();
    userResponse = UserTestUtils.getUserResponse();
    createRequest = UserTestUtils.getUserCreateRequest();
    updateRequest = UserTestUtils.getUserUpdateRequest();
  }

  @DisplayName("Create user - valid data")
  @Test
  void saveOne_ShouldReturnCreatedUserResponse() {
    // Arrange
    when(mapper.createRequestToDomain(any(UserCreateRequest.class))).thenReturn(userModel);
    when(createUseCase.createOne(any(UserModel.class))).thenReturn(userModel);
    when(mapper.toUserResponse(any(UserModel.class))).thenReturn(userResponse);

    // Act
    var response = userController.saveOne(createRequest);

    // Assert
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(userResponse, response.getBody());
    verify(mapper, times(1)).createRequestToDomain(any(UserCreateRequest.class));
    verify(createUseCase, times(1)).createOne(any(UserModel.class));
    verify(mapper, times(1)).toUserResponse(any(UserModel.class));
  }

  @DisplayName("Retrieve user - valid data")
  @Test
  void getUserByDocumentId_WhenDocumentIdIsProvided_ShouldReturnUserResponse() {
    // Arrange
    when(retrieveUseCase.findByDocumentId(anyString())).thenReturn(userModel);
    when(mapper.toUserResponse(any(UserModel.class))).thenReturn(userResponse);

    // Act
    var response = userController.getUserByDocumentId(documentId);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(userResponse, response.getBody());
    verify(mapper, times(1)).toUserResponse(any(UserModel.class));
    verify(retrieveUseCase, times(1)).findByDocumentId(anyString());
  }

  @DisplayName("Update user - valid data")
  @Test
  void updateUserByDocumentId_WhenUserDataIsValid() {
    // Arrange
    when(mapper.updateRequestToDomain(any(UserUpdateRequest.class))).thenReturn(userModel);
    doNothing().when(updateUseCase).updateByDocumentId(anyString(), any(UserModel.class));

    // Act
    var response = userController.updateUserByDocumentId(documentId, updateRequest);

    // Assert
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(mapper, times(1)).updateRequestToDomain(any(UserUpdateRequest.class));
    verify(updateUseCase, times(1)).updateByDocumentId(anyString(), any(UserModel.class));
  }
}