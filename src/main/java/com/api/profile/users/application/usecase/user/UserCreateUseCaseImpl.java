package com.api.profile.users.application.usecase.user;

import com.api.profile.users.application.port.in.user.UserCreateUseCase;
import com.api.profile.users.application.port.out.UserPersistencePort;
import com.api.profile.users.domain.model.user.UserModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service class that implements the use case for managing the user creation.
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class UserCreateUseCaseImpl implements UserCreateUseCase {

  private final UserPersistencePort persistencePort;

  /**
   * {@inheritDoc}
   */
  @Override
  public UserModel createOne(UserModel data) {
    log.info("Starting user creation process");
    return persistencePort.save(data);
  }
}
