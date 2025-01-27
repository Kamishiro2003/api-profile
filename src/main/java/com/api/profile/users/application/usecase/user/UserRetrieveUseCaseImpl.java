package com.api.profile.users.application.usecase.user;

import com.api.profile.users.application.port.in.user.UserRetrieveUseCase;
import com.api.profile.users.application.port.out.UserPersistencePort;
import com.api.profile.users.domain.exception.InstanceNotFoundException;
import com.api.profile.users.domain.exception.MissingParameterException;
import com.api.profile.users.domain.model.user.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class that implements the use case for managing user retrieving.
 */
@Service
@RequiredArgsConstructor
public class UserRetrieveUseCaseImpl implements UserRetrieveUseCase {

  private final UserPersistencePort persistencePort;

  @Override
  public UserModel findByDocumentId(String data) {

    if (data == null) {
      throw new MissingParameterException("documentId");
    }
    return persistencePort.findByDocumentId(data)
        .orElseThrow(() -> new InstanceNotFoundException("USER"));
  }
}
