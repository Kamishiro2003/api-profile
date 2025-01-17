package com.api.profile.users.util.application.adapter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.api.profile.users.domain.model.user.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.UserTestUtils;

class PasswordHashAdapterTests {

  private PasswordHashAdapter adapter;

  private UserModel user;

  @BeforeEach
  void setUp() {

    user = UserTestUtils.getValiduserModel();
    adapter = new PasswordHashAdapter();
  }

  @DisplayName("Hashing user password - Password is provided")
  @Test
  void hashPassword_WhenPasswordIsProvided_ShouldReturnPasswordHashed() {
    // Act
    String hash = adapter.hashPassword(user.getPassword());
    // Assert
    assertNotNull(hash);
  }
}