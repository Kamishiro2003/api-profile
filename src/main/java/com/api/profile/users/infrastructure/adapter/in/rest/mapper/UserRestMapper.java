package com.api.profile.users.infrastructure.adapter.in.rest.mapper;

import com.api.profile.users.domain.model.user.PasswordModel;
import com.api.profile.users.domain.model.user.UserModel;
import com.api.profile.users.infrastructure.adapter.in.rest.model.request.PasswordUpdateRequest;
import com.api.profile.users.infrastructure.adapter.in.rest.model.request.UserCreateRequest;
import com.api.profile.users.infrastructure.adapter.in.rest.model.request.UserUpdateRequest;
import com.api.profile.users.infrastructure.adapter.in.rest.model.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between REST request/response models and domain models.
 */
@Component
public class UserRestMapper {
  /**
   * Convert a {@link UserCreateRequest} to an {@link UserModel}.
   *
   * @param request the create request
   * @return the domain model
   */
  public UserModel createRequestToDomain(UserCreateRequest request) {
    return UserModel.builder()
        .name(request.getName())
        .lastName(request.getLastName())
        .documentType(request.getDocumentType())
        .documentId(request.getDocumentId())
        .phoneNumber(request.getPhoneNumber())
        .address(request.getAddress())
        .email(request.getEmail())
        .password(request.getPassword())
        .role(request.getRole())
        .build();
  }

  /**
   * Convert a {@link UserUpdateRequest} to an {@link UserModel}.
   *
   * @param request the update request
   * @return the domain model
   */
  public UserModel updateRequestToDomain(UserUpdateRequest request) {
    return UserModel.builder()
        .name(request.getName())
        .lastName(request.getLastName())
        .documentType(request.getDocumentType())
        .documentId(request.getDocumentId())
        .phoneNumber(request.getPhoneNumber())
        .address(request.getAddress())
        .email(request.getEmail())
        .role(request.getRole())
        .build();
  }

  /**
   * Convert a {@link PasswordUpdateRequest} to a {@link PasswordModel}.
   *
   * @param data the update request
   * @return the domain model
   */
  public PasswordModel toPasswordModel(String username, PasswordUpdateRequest data) {
    return PasswordModel.builder()
        .documentId(username)
        .oldPassword(data.getOldPassword())
        .newPassword(data.getNewPassword())
        .confirmPassword(data.getConfirmPassword())
        .build();
  }

  /**
   * Convert a {@link UserModel} to a {@link UserResponse}.
   *
   * @param data the domain model
   * @return the response model
   */
  public UserResponse toUserResponse(UserModel data) {
    return UserResponse.builder()
        .name(data.getName())
        .lastName(data.getLastName())
        .documentType(data.getDocumentType())
        .documentId(data.getDocumentId())
        .phoneNumber(data.getPhoneNumber())
        .address(data.getAddress())
        .email(data.getEmail())
        .role(data.getRole())
        .createdAt(data.getCreatedAt())
        .updatedAt(data.getUpdatedAt())
        .isActive(data.getIsActive())
        .build();
  }
}
