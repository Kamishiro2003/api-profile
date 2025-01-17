package com.api.profile.users.infrastructure.adapter.out.persistence.repository;

import com.api.profile.users.infrastructure.adapter.out.persistence.model.UserPersistenceModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for user persistence operations.
 */
public interface UserPersistenceRepository
    extends JpaRepository<UserPersistenceModel, String> {

  /**
   * find a user by its documentId.
   *
   * @param documentId the documentId of the user.
   * @return an {@code Optional} containing the user data, or empty if not found.
   */
  Optional<UserPersistenceRepository> findByDocumentId(String documentId);
}
