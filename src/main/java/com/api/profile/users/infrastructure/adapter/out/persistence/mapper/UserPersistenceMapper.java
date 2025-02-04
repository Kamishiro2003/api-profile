package com.api.profile.users.infrastructure.adapter.out.persistence.mapper;

import com.api.profile.users.domain.model.user.UserModel;
import com.api.profile.users.infrastructure.adapter.out.persistence.model.UserPersistenceModel;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between {@link UserPersistenceModel} and {@link UserModel}.
 */
@Component
public class UserPersistenceMapper {

  /**
   * Convert a {@link UserModel} to a {@link UserPersistenceModel}.
   *
   * @param data the model to convert.
   * @return the persistence model.
   */
  public UserPersistenceModel toPersistence(UserModel data) {

    return UserPersistenceModel.builder()
        .id(data.getId())
        .name(data.getName())
        .lastName(data.getLastName())
        .documentType(data.getDocumentType())
        .documentId(data.getDocumentId())
        .phoneNumber(data.getPhoneNumber())
        .address(data.getAddress())
        .email(data.getEmail())
        .password(data.getPassword())
        .role(data.getRole())
        .createdAt(data.getCreatedAt())
        .updatedAt(data.getUpdatedAt())
        .isActive(data.getIsActive())
        .build();
  }

  /**
   * Convert a {@link UserPersistenceModel} to a {@link UserModel}.
   *
   * @param data the model to convert.
   * @return the domain model.
   */
  public UserModel toDomain(UserPersistenceModel data) {

    return UserModel.builder()
        .id(data.getId())
        .name(data.getName())
        .lastName(data.getLastName())
        .documentType(data.getDocumentType())
        .documentId(data.getDocumentId())
        .phoneNumber(data.getPhoneNumber())
        .address(data.getAddress())
        .email(data.getEmail())
        .password(data.getPassword())
        .role(data.getRole())
        .createdAt(data.getCreatedAt())
        .updatedAt(data.getUpdatedAt())
        .isActive(data.getIsActive())
        .build();
  }
}
