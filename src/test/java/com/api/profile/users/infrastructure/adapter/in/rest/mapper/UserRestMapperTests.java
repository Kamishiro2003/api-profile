package com.api.profile.users.infrastructure.adapter.in.rest.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.UserTestUtils;

class UserRestMapperTests {

  private UserRestMapper restMapper;

  @BeforeEach
  void setUp() {

    restMapper = new UserRestMapper();
  }

  @DisplayName("Convert UserCreateRequest to UserModel")
  @Test
  void createRequestToDomain_ConvertUserCreateRequestToUserModel_ShouldReturnUserModel() {
    // Arrange
    var userCreateRequest = UserTestUtils.getUserCreateRequest();

    // Act
    var userModel = restMapper.createRequestToDomain(userCreateRequest);

    // Assert
    assertEquals(userCreateRequest.getDocumentId(), userModel.getDocumentId());
    assertEquals(userCreateRequest.getPassword(), userModel.getPassword());
    assertEquals(userCreateRequest.getEmail(), userModel.getEmail());
    assertEquals(userCreateRequest.getLastName(), userModel.getLastName());
    assertEquals(userCreateRequest.getRole(), userModel.getRole());
  }

  @DisplayName("Convert UserUpdateRequest to UserModel")
  @Test
  void updateRequestToDomain_ConvertUserUpdateRequestToUserModel_ShouldReturnUserModel() {
    // Arrange
    var userUpdateRequest = UserTestUtils.getUserUpdateRequest();

    // Act
    var userModel = restMapper.updateRequestToDomain(userUpdateRequest);

    // Assert
    assertEquals(userUpdateRequest.getDocumentId(), userModel.getDocumentId());
    assertEquals(userUpdateRequest.getEmail(), userModel.getEmail());
    assertEquals(userUpdateRequest.getLastName(), userModel.getLastName());
    assertEquals(userUpdateRequest.getRole(), userModel.getRole());
  }

  @DisplayName("Convert UserModel to UserResponse")
  @Test
  void toUserResponse_ConvertUserModelToUserResponse_ShouldReturnUserResponse() {
    // Arrange
    var userModel = UserTestUtils.getValiduserModel();

    // Act
    var userResponse = restMapper.toUserResponse(userModel);

    // Assert
    assertEquals(userResponse.getDocumentId(), userModel.getDocumentId());
    assertEquals(userResponse.getEmail(), userModel.getEmail());
    assertEquals(userResponse.getLastName(), userModel.getLastName());
    assertEquals(userResponse.getRole(), userModel.getRole());
  }
}