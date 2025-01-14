package com.api.profile.users.application.usecase.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.profile.users.application.port.out.UserPersistencePort;
import com.api.profile.users.domain.model.user.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.UserTestUtils;

@ExtendWith(MockitoExtension.class)
class UserCreateUseCaseImplTest {

  private UserModel user;

  @Mock
  private UserPersistencePort persistencePort;

  @InjectMocks
  private UserCreateUseCaseImpl createUseCase;

  @BeforeEach
  void setUp() {
    user = UserTestUtils.getValiduserModel();
  }

  @DisplayName("Create user - valid data")
  @Test
  void createOne_WhenUserDataIsValid_ShouldReturnUserCreated() {
    // Arrange
    when(persistencePort.save(any(UserModel.class))).thenReturn(user);

    // Act
    UserModel userCreated = createUseCase.createOne(user);

    // Assert
    assertNotNull(userCreated);
    assertEquals(user, userCreated);
    verify(persistencePort, times(1)).save(any(UserModel.class));
  }
}