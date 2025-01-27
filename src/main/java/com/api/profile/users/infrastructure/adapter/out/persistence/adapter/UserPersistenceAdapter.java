package com.api.profile.users.infrastructure.adapter.out.persistence.adapter;

import com.api.profile.users.application.port.out.UserPersistencePort;
import com.api.profile.users.domain.model.user.UserModel;
import com.api.profile.users.infrastructure.adapter.out.persistence.mapper.UserPersistenceMapper;
import com.api.profile.users.infrastructure.adapter.out.persistence.repository.UserPersistenceRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Adapter for user persistence operations, implementing {@link UserPersistencePort}.
 */
@RequiredArgsConstructor
@Component
public class UserPersistenceAdapter implements UserPersistencePort {

  private final UserPersistenceRepository persistenceRepository;

  private final UserPersistenceMapper mapper;

  /**
   * {@inheritDoc}
   */
  @Override
  public UserModel save(UserModel data) {
    return mapper.toDomain(persistenceRepository.save(mapper.toPersistence(data)));
  }

  @Override
  public Optional<UserModel> findByDocumentId(String data) {
    return persistenceRepository.findByDocumentId(data).map(mapper::toDomain);
  }
}
