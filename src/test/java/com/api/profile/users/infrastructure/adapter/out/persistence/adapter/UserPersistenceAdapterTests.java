package com.api.profile.users.infrastructure.adapter.out.persistence.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.profile.users.domain.model.user.UserModel;
import com.api.profile.users.infrastructure.adapter.out.persistence.mapper.UserPersistenceMapper;
import com.api.profile.users.infrastructure.adapter.out.persistence.model.UserPersistenceModel;
import com.api.profile.users.infrastructure.adapter.out.persistence.repository.UserPersistenceRepository;
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
    assertEquals(userModel, result);
    verify(repository, times(1)).save(any(UserPersistenceModel.class));
  }
}