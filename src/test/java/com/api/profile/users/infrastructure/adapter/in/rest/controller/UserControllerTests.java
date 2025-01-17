package com.api.profile.users.infrastructure.adapter.in.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.profile.users.application.port.in.user.UserCreateUseCase;
import com.api.profile.users.domain.model.user.UserModel;
import com.api.profile.users.infrastructure.adapter.in.rest.mapper.UserRestMapper;
import com.api.profile.users.infrastructure.adapter.in.rest.model.request.UserCreateRequest;
import com.api.profile.users.infrastructure.adapter.in.rest.model.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import utils.UserTestUtils;

@ExtendWith(MockitoExtension.class)
class UserControllerTests {

  @Mock
  private UserCreateUseCase createUseCase;

  @Mock
  private UserRestMapper mapper;

  @InjectMocks
  private UserController userController;

  private UserCreateRequest createRequest;

  private UserModel userModel;

  private UserResponse userResponse;

  @BeforeEach
  void setUp() {

    userModel = UserTestUtils.getValiduserModel();
    userResponse = UserTestUtils.getUserResponse();
    createRequest = UserTestUtils.getUserCreateRequest();
  }

  @DisplayName("Create user - valid data")
  @Test
  void saveOne_ShouldReturnCreatedUserResponse() {
    // Arrange
    when(mapper.createRequestToDomain(any(UserCreateRequest.class))).thenReturn(userModel);
    when(createUseCase.createOne(any(UserModel.class))).thenReturn(userModel);
    when(mapper.toUserResponse(any(UserModel.class))).thenReturn(userResponse);

    // Act
    ResponseEntity<UserResponse> response = userController.saveOne(createRequest);

    // Assert
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(userResponse, response.getBody());
    verify(createUseCase, times(1)).createOne(any(UserModel.class));
  }

}