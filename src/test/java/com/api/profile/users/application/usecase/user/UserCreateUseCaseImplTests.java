package com.api.profile.users.application.usecase.user;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.profile.users.application.port.out.UserPersistencePort;
import com.api.profile.users.domain.exception.FieldAlreadyExistException;
import com.api.profile.users.domain.model.user.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import utils.UserTestUtils;

@ExtendWith(MockitoExtension.class)
class UserCreateUseCaseImplTests {

  private UserModel user;

  @Mock
  private UserPersistencePort persistencePort;

  @Mock
  private PasswordEncoder passwordEncoder;

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
    var passwordHashed = passwordEncoder.encode(user.getPassword());
    when(passwordEncoder.encode(any(String.class))).thenReturn(passwordHashed);
    when(persistencePort.save(any(UserModel.class))).thenReturn(user);

    // Act
    var userCreated = createUseCase.createOne(user);

    // Assert
    assertNotNull(userCreated);
    verify(persistencePort, times(1)).save(any(UserModel.class));
  }

  @ParameterizedTest
  @DisplayName("Create user - Unique Constraint Violation")
  @CsvSource(
      {
          "documentId_unique_constraint",
          "email_unique_constraint",
          "other exception"
      }
  )
  void createByOne_WhenDataIntegrityViolation_ShouldThrowFieldAlreadyExistException(String constraint
  ) {
    // Arrange
    when(persistencePort.save(any(UserModel.class))).thenThrow(new DataIntegrityViolationException(
        constraint));

    // Act & Assert
    if ("email_unique_constraint".equals(constraint) ||
        "documentId_unique_constraint".equals(constraint)) {
      assertThrows(FieldAlreadyExistException.class, () -> createUseCase.createOne(user));
    } else {
      assertThrows(DataIntegrityViolationException.class, () -> createUseCase.createOne(user));
    }

    verify(persistencePort, times(1)).save(any(UserModel.class));
  }


}