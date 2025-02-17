package com.api.profile.users.application.usecase.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.profile.users.application.port.out.UserPersistencePort;
import com.api.profile.users.domain.exception.FieldAlreadyExistException;
import com.api.profile.users.domain.exception.InstanceNotFoundException;
import com.api.profile.users.domain.exception.InvalidPasswordException;
import com.api.profile.users.domain.exception.MissingParameterException;
import com.api.profile.users.domain.model.user.PasswordModel;
import com.api.profile.users.domain.model.user.UserModel;
import java.util.Optional;
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
class UserUpdateUseCaseImplTests {

  private UserModel userModel;

  private String documentId;

  private PasswordModel passwordModel;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private UserPersistencePort persistencePort;

  @InjectMocks
  private UserUpdateUseCaseImpl updateUseCase;

  @BeforeEach
  void setUp() {

    passwordModel = UserTestUtils.getPasswordModel();
    userModel = UserTestUtils.getValiduserModel();
    documentId = userModel.getDocumentId();
  }

  @DisplayName("Update User by its document id - valid data")
  @Test
  void updateByDocumentId_WhenUserDataIsValid() {
    // Arrange
    userModel.setLastName("Alberto");
    userModel.setEmail("example@gmail.com");
    userModel.setDocumentId("135468486");

    when(persistencePort.findByDocumentId(anyString())).thenReturn(Optional.of(userModel));
    when(persistencePort.save(any(UserModel.class))).thenReturn(userModel);

    // Act
    updateUseCase.updateByDocumentId(documentId, userModel);

    // Assert
    assertEquals("Alberto", userModel.getLastName());
    assertEquals("example@gmail.com", userModel.getEmail());
    verify(persistencePort, times(1)).findByDocumentId(anyString());
    verify(persistencePort, times(1)).save(any(UserModel.class));
  }

  @DisplayName("Update User by its document id - missing documentId")
  @Test
  void updateByDocumentId_WhenDocumentIdIsNotProvided_ShouldThrowMissingParameterException() {
    // Arrange
    documentId = null;
    // Act && Assert
    assertThrows(
        MissingParameterException.class,
        () -> updateUseCase.updateByDocumentId(documentId, userModel)
    );
  }

  @DisplayName("Update User by its document id - User Not Found")
  @Test
  void updateByDocumentId_WhenUserWasNotFound_ShouldThrowInstanceNotFoundException() {
    //Arrange
    when(persistencePort.findByDocumentId(anyString())).thenReturn(Optional.empty());

    //Act & Assert
    assertThrows(
        InstanceNotFoundException.class,
        () -> updateUseCase.updateByDocumentId(documentId, userModel)
    );
    verify(persistencePort, times(1)).findByDocumentId(anyString());
  }

  @ParameterizedTest
  @CsvSource(
      {
          "documentId_unique_constraint",
          "email_unique_constraint",
          "other exception"
      }
  )
  @DisplayName("Update User by its document id - Unique Constraint Violation")
  void updateByDocumentId_WhenDataIntegrityViolation_ShouldThrowFieldAlreadyExistException(String constraint
  ) {
    //Arrange
    when(persistencePort.findByDocumentId(anyString())).thenReturn(Optional.of(userModel));
    when(persistencePort.save(any(UserModel.class))).thenThrow(new DataIntegrityViolationException(
        constraint));

    // Act & Assert
    if ("email_unique_constraint".equals(constraint) ||
        "documentId_unique_constraint".equals(constraint)) {
      assertThrows(
          FieldAlreadyExistException.class,
          () -> updateUseCase.updateByDocumentId(documentId, userModel)
      );
    } else {
      assertThrows(
          DataIntegrityViolationException.class,
          () -> updateUseCase.updateByDocumentId(documentId, userModel)
      );
    }
    verify(persistencePort, times(1)).findByDocumentId(anyString());
    verify(persistencePort, times(1)).save(any(UserModel.class));
  }


  @DisplayName("Update password - valid data")
  @Test
  void updatePassword_WhenPasswordModelIsProvided_ShouldUpdatePassword() {
    // Arrange
    String encodedPassword = "encodedPassword";
    userModel.setPassword("encodedPassword");
    when(persistencePort.findByDocumentId(anyString())).thenReturn(Optional.of(userModel));
    when(passwordEncoder.matches(
        passwordModel.getOldPassword(),
        userModel.getPassword()
    )).thenReturn(true);
    when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);

    // Act
    updateUseCase.updatePassword(passwordModel);

    // Assert
    assertEquals(encodedPassword, userModel.getPassword());
    verify(persistencePort, times(1)).findByDocumentId(anyString());
    verify(passwordEncoder, times(1)).matches(
        passwordModel.getOldPassword(),
        userModel.getPassword()
    );
    verify(passwordEncoder, times(1)).encode(anyString());
  }

  @DisplayName("Update password - new password and confirm password do not match")
  @Test
  void updatePassword_WhenNewPasswordAndConfirmPasswordDoNotMatch_ShouldThrowInvalidPasswordException() {
    // Arrange
    passwordModel.setConfirmPassword("differentPassword");

    // Act & Assert
    assertThrows(
        InvalidPasswordException.class, () -> {
          updateUseCase.updatePassword(passwordModel);
        }
    );
    verify(persistencePort, never()).save(any(UserModel.class));
  }

  @DisplayName("Update password - invalid old password")
  @Test
  void updatePassword_WhenOldPasswordDoesNotMatch_ShouldThrowInvalidPasswordException() {
    // Arrange
    when(persistencePort.findByDocumentId(passwordModel.getDocumentId())).thenReturn(Optional.of(
        userModel));
    when(passwordEncoder.matches(
        passwordModel.getOldPassword(),
        userModel.getPassword()
    )).thenReturn(false);

    // Act & Assert
    InvalidPasswordException exception = assertThrows(
        InvalidPasswordException.class, () -> {
          updateUseCase.updatePassword(passwordModel);
        }
    );

    assertEquals("password provided is incorrect", exception.getMessage());
    verify(persistencePort, times(1)).findByDocumentId(anyString());
    verify(passwordEncoder, times(1)).matches(
        passwordModel.getOldPassword(),
        userModel.getPassword()
    );
    verify(persistencePort, never()).save(any(UserModel.class));
  }


}