package com.api.profile.users.application.usecase.user;

import com.api.profile.users.application.port.in.user.UserUpdateUseCase;
import com.api.profile.users.application.port.out.UserPersistencePort;
import com.api.profile.users.domain.exception.FieldAlreadyExistException;
import com.api.profile.users.domain.exception.InstanceNotFoundException;
import com.api.profile.users.domain.exception.MissingParameterException;
import com.api.profile.users.domain.model.user.UserModel;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * Service class that implements the use case for updating the user data.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserUpdateUseCaseImpl implements UserUpdateUseCase {

  private final UserPersistencePort persistencePort;

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateByDocumentId(String documentId, UserModel data) {
    log.info("updating user from application: {}", documentId);
    if (documentId == null) {
      throw new MissingParameterException("documentId");
    }
    persistencePort.findByDocumentId(documentId).ifPresentOrElse(
        userToUpdate -> {
          userToUpdate.setName(data.getName());
          userToUpdate.setLastName(data.getLastName());
          userToUpdate.setDocumentType(data.getDocumentType());
          userToUpdate.setDocumentId(data.getDocumentId());
          userToUpdate.setPhoneNumber(data.getPhoneNumber());
          userToUpdate.setAddress(data.getAddress());
          userToUpdate.setEmail(data.getEmail());
          userToUpdate.setRole(data.getRole());
          userToUpdate.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));
          userToUpdate.setIsActive(data.getIsActive());
          try {
            log.debug("user to update: {}", data);
            persistencePort.save(userToUpdate);
          } catch (DataIntegrityViolationException e) {
            log.error("caught exception: {}", e.getMessage());
            if (e.getMessage().trim().contains("documentId_unique_constraint")) {
              throw new FieldAlreadyExistException(
                  "DocumentId already exist, please insert another DocumentId",
                  "DOCUMENT-ID-ALREADY-EXIST"
              );
            } else if (e.getMessage().trim().contains("email_unique_constraint")) {
              throw new FieldAlreadyExistException(
                  "Email already exist, please insert another " + "email",
                  "EMAIL-ALREADY-EXIST"
              );
            } else {
              throw e;
            }
          }
        }, () -> {
          throw new InstanceNotFoundException("USER");
        }
    );
  }
}
