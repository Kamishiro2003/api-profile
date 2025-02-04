package com.api.profile.users.infrastructure.adapter.out.persistence.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.profile.users.domain.model.user.UserModel;
import com.api.profile.users.infrastructure.adapter.out.persistence.mapper.UserPersistenceMapper;
import com.api.profile.users.infrastructure.adapter.out.persistence.model.UserPersistenceModel;
import com.api.profile.users.infrastructure.adapter.out.persistence.repository.UserPersistenceRepository;
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
class UserPersistenceAdapterTests {

  @Mock
  private UserPersistenceRepository repository;

  @Mock
  private UserPersistenceMapper mapper;

  @InjectMocks
  private UserPersistenceAdapter adapter;

  private UserModel userModel;

  private UserPersistenceModel userPersistenceModel;

  @BeforeEach
  void setUp() {

    userModel = UserTestUtils.getValiduserModel();
    userPersistenceModel = UserTestUtils.getValidUserPersistence();
  }

  @DisplayName("saving persistence user")
  @Test
  void save_WhenValidDataProvided_ShouldReturnSavedUserModel() {
    // Arrange
    when(mapper.toPersistence(any(UserModel.class))).thenReturn(userPersistenceModel);
    when(repository.save(any(UserPersistenceModel.class))).thenReturn(userPersistenceModel);
    when(mapper.toDomain(any(UserPersistenceModel.class))).thenReturn(userModel);

    // Act
    var result = adapter.save(userModel);

    // Assert
    assertEquals(userPersistenceModel.getEmail(), result.getEmail());
    assertEquals(userPersistenceModel.getPhoneNumber(), result.getPhoneNumber());
    assertEquals(userPersistenceModel.getDocumentId(), result.getDocumentId());
    verify(mapper, times(1)).toPersistence(any(UserModel.class));
    verify(repository, times(1)).save(any(UserPersistenceModel.class));
    verify(mapper, times(1)).toDomain(any(UserPersistenceModel.class));
  }

  @DisplayName("finding persistence user")
  @Test
  void findByDocumentId_WhenDocumentIdIsProvided_ShouldReturnRetrievedUserModel() {
    // Arrange
    when(repository.findByDocumentId(anyString())).thenReturn(Optional.of(userPersistenceModel));
    when(mapper.toDomain(any(UserPersistenceModel.class))).thenReturn(userModel);

    // Act
    var retrievedUser = adapter.findByDocumentId(userModel.getDocumentId());

    // Assert
    assertNotNull(retrievedUser);
    verify(repository, times(1)).findByDocumentId(anyString());
    verify(mapper, times(1)).toDomain(any(UserPersistenceModel.class));
  }
}