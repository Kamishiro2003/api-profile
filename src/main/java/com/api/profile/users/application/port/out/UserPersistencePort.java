package com.api.profile.users.application.port.out;

import com.api.profile.users.domain.model.user.UserModel;

/**
 * Persistence operations for user data.
 */
public interface UserPersistencePort {
  /**
   * Saves a new or existing user.
   *
   * @param data the user to save.
   * @return the saved user.
   */
  UserModel save(UserModel data);
}
