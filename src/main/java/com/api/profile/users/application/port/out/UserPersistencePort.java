package com.api.profile.users.application.port.out;

import com.api.profile.users.domain.model.user.UserModel;
import java.util.Optional;

/**
 * Persistence operations for user data.
 */
public interface UserPersistencePort {

  /**
   * Save a new or existing user.
   *
   * @param data the user to save
   * @return the saved user
   */
  UserModel save(UserModel data);

  /**
   * Find a user by its document id.
   *
   * @param data the document id of the user
   * @return an {@code Optional} containing the found user, or empty if not found
   */
  Optional<UserModel> findByDocumentId(String data);

}
