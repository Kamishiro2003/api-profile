package com.api.profile.users.application.usecase.user;

import com.api.profile.users.application.port.in.user.UserCreateUseCase;
import com.api.profile.users.application.port.out.UserPersistencePort;
import com.api.profile.users.domain.exception.FieldAlreadyExistException;
import com.api.profile.users.domain.model.user.UserModel;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class that implements the use case for managing the user creation.
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class UserCreateUseCaseImpl implements UserCreateUseCase {

  private final UserPersistencePort persistencePort;

  private final PasswordEncoder passwordEncoder;

  /**
   * {@inheritDoc}
   */
  @Override
  public UserModel createOne(UserModel data) {

    log.info("Starting user creation process");
    LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
    data.setCreatedAt(now);
    data.setUpdatedAt(now);
    data.setIsActive(true);
    data.setPassword(passwordEncoder.encode(data.getPassword()));
    try {
      log.debug("saving user: {}", data);
      return persistencePort.save(data);
    } catch (DataIntegrityViolationException e) {
      log.error("Caught exception: {}", e.getMessage());
      if (e.getMessage().trim().contains("documentId_unique_constraint")) {
        throw new FieldAlreadyExistException(
            "DocumentId already exist, please insert another DocumentId",
            "DOCUMENT-ID-ALREADY-EXIST"
        );
      } else if (e.getMessage().trim().contains("email_unique_constraint")) {
        throw new FieldAlreadyExistException(
            "Email already exist, please insert another email",
            "EMAIL-ALREADY-EXIST"
        );
      } else {
        throw e;
      }
    }
  }
}
