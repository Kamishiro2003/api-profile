package com.api.profile.users.infrastructure.adapter.out.persistence.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.UserTestUtils;

class UserPersistenceMapperTests {

  private UserPersistenceMapper mapper;

  @BeforeEach
  void setUp() {

    mapper = new UserPersistenceMapper();
  }

  @DisplayName("Convert UserModel to UserPersistenceModel")
  @Test
  void toPersistence_ConvertUserModelToUserPersistenceModel_ShouldReturnUserPersistenceModel() {
    // Arrange
    var userModel = UserTestUtils.getValiduserModel();

    // Act
    var userPersistence = mapper.toPersistence(userModel);

    // Assert
    assertEquals(userPersistence.getDocumentId(), userModel.getDocumentId());
    assertEquals(userPersistence.getPassword(), userModel.getPassword());
    assertEquals(userPersistence.getEmail(), userModel.getEmail());
    assertEquals(userPersistence.getLastName(), userModel.getLastName());
    assertEquals(userPersistence.getRole(), userModel.getRole());
  }

  @DisplayName("Convert UserPersistenceModel to UserModel")
  @Test
  void toDomain_ConvertUserPersistenceModelToUserModel_ShouldReturnUserModel() {
    // Arrange
    var userPersistence = UserTestUtils.getValidUserPersistence();

    // Act
    var userModel = mapper.toDomain(userPersistence);

    // Assert
    assertEquals(userPersistence.getDocumentId(), userModel.getDocumentId());
    assertEquals(userPersistence.getPassword(), userModel.getPassword());
    assertEquals(userPersistence.getEmail(), userModel.getEmail());
    assertEquals(userPersistence.getLastName(), userModel.getLastName());
    assertEquals(userPersistence.getRole(), userModel.getRole());
  }
}