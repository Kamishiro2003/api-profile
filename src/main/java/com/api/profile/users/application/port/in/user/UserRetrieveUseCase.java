package com.api.profile.users.application.port.in.user;

import com.api.profile.users.domain.model.user.UserModel;

/**
 * Use case for retrieving users.
 */
public interface UserRetrieveUseCase {

  /**
   * Find user by its documentId.
   *
   * @param data the document id of the user.
   * @return the user with the specific document id.
   */
  UserModel findByDocumentId(String data);
}
