package com.api.profile.users.application.port.in.user;

import com.api.profile.users.domain.model.user.UserModel;

/**
 * Use case for creating a user.
 */
public interface UserCreateUseCase {

  /**
   * Creates a new user.
   *
   * @param data the user to create.
   * @return the created user.
   */
  UserModel createOne(UserModel data);
}
