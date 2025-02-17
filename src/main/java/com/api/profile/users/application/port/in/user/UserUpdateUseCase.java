package com.api.profile.users.application.port.in.user;

import com.api.profile.users.domain.model.user.PasswordModel;
import com.api.profile.users.domain.model.user.UserModel;

/**
 * Use case for updating the user data.
 */
public interface UserUpdateUseCase {

  /**
   * Update user by its document id.
   *
   * @param documentId the document id of the user
   * @param data the data of the user
   */
  void updateByDocumentId(String documentId, UserModel data);

  /**
   * Update user password.
   *
   * @param data the details for changing user password.
   */
  void updatePassword(PasswordModel data);
}
