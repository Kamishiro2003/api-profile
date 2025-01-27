package com.api.profile.users.application.usecase.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.profile.users.application.port.out.UserPersistencePort;
import com.api.profile.users.domain.exception.InstanceNotFoundException;
import com.api.profile.users.domain.exception.MissingParameterException;
import com.api.profile.users.domain.model.user.UserModel;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.UserTestUtils;

@ExtendWith(MockitoExtension.class)
class UserRetrieveUseCaseImplTests {

  @Mock
  private UserPersistencePort persistencePort;

  @InjectMocks
  private UserRetrieveUseCaseImpl retrieveUseCase;

  private UserModel userModel;

  private String documentId;

  @BeforeEach
  void setUp() {

    userModel = UserTestUtils.getValiduserModel();
    documentId = UserTestUtils.getValiduserModel().getDocumentId();
  }

  @DisplayName("Find user by document id - valid data")
  @Test
  void findByDocumentId_WhenDocumentIdIsProvided_ShouldReturnUserModel() {
    // Arrange
    when(persistencePort.findByDocumentId(anyString())).thenReturn(Optional.of(userModel));

    // Act
    var userOptional = retrieveUseCase.findByDocumentId(documentId);

    // Assert
    assertNotNull(userOptional);
    assertEquals(documentId, userOptional.getDocumentId());
    verify(persistencePort, times(1)).findByDocumentId(anyString());
  }

  @DisplayName("Find user by document id - invalid parameter")
  @Test
  void findByDocumentId_WhenDocumentIdIsNotProvided_ShouldThrowMissingParameterException() {
    // Act && Assert
    assertThrows(MissingParameterException.class, () -> retrieveUseCase.findByDocumentId(null));
  }

  @DisplayName("Find user by document id - user not found")
  @Test
  void findByDocumentId_WhenIsNotFound_ShouldThrowInstanceNotFoundException() {
    // Arrange
    when(persistencePort.findByDocumentId(anyString())).thenReturn(Optional.empty());

    // Act && Assert
    assertThrows(InstanceNotFoundException.class,
        () -> retrieveUseCase.findByDocumentId(documentId));
    verify(persistencePort, times(1)).findByDocumentId(anyString());
  }
}