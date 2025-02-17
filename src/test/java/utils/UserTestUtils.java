package utils;

import com.api.profile.users.domain.model.user.DocumentType;
import com.api.profile.users.domain.model.user.PasswordModel;
import com.api.profile.users.domain.model.user.RoleEnum;
import com.api.profile.users.domain.model.user.UserModel;
import com.api.profile.users.infrastructure.adapter.in.rest.model.request.PasswordUpdateRequest;
import com.api.profile.users.infrastructure.adapter.in.rest.model.request.UserCreateRequest;
import com.api.profile.users.infrastructure.adapter.in.rest.model.request.UserUpdateRequest;
import com.api.profile.users.infrastructure.adapter.in.rest.model.response.UserResponse;
import com.api.profile.users.infrastructure.adapter.out.persistence.model.UserPersistenceModel;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import org.springframework.security.core.userdetails.UserDetails;

public class UserTestUtils {
  public static UserModel getValiduserModel() {
    return UserModel.builder()
        .id(UUID.randomUUID().toString())
        .name("pedro")
        .lastName("perez")
        .documentType(DocumentType.CC)
        .documentId("765892644")
        .phoneNumber("3025482546")
        .email("pedro.perez@gmail.com")
        .password("dead252003")
        .role(RoleEnum.ADMIN)
        .isActive(true)
        .createdAt(LocalDateTime.now(ZoneOffset.UTC))
        .updatedAt(LocalDateTime.now(ZoneOffset.UTC))
        .build();
  }
  public static UserPersistenceModel getValidUserPersistence() {
    return UserPersistenceModel.builder()
        .id(UUID.randomUUID().toString())
        .name("pedro")
        .lastName("perez")
        .documentType(DocumentType.CC)
        .documentId("765892644")
        .phoneNumber("3025482546")
        .email("pedro.perez@gmail.com")
        .password("dead252003")
        .role(RoleEnum.ADMIN)
        .isActive(true)
        .createdAt(LocalDateTime.now(ZoneOffset.UTC))
        .updatedAt(LocalDateTime.now(ZoneOffset.UTC))
        .build();
  }
  public static UserModel getInvaliduserModel() {
    return UserModel.builder()
        .name("pedro")
        .lastName("perez")
        .email("pedro.perez@gmail.com")
        .updatedAt(LocalDateTime.now(ZoneOffset.UTC))
        .build();
  }
  public static UserCreateRequest getUserCreateRequest() {
    return UserCreateRequest.builder()
        .name("pedro")
        .lastName("perez")
        .documentType(DocumentType.CC)
        .documentId("765892644")
        .phoneNumber("3025482546")
        .email("pedro.perez@gmail.com")
        .password("dead252003")
        .role(RoleEnum.ADMIN)
        .build();
  }
  public static UserUpdateRequest getUserUpdateRequest() {
    return UserUpdateRequest.builder()
        .name("pedro")
        .lastName("perez")
        .documentType(DocumentType.CC)
        .documentId("765892644")
        .phoneNumber("3025482546")
        .email("pedro.perez@gmail.com")
        .role(RoleEnum.ADMIN)
        .build();
  }
  public static UserResponse getUserResponse() {
    return UserResponse.builder()
        .name("pedro")
        .lastName("perez")
        .documentType(DocumentType.CC)
        .documentId("765892644")
        .phoneNumber("3025482546")
        .email("pedro.perez@gmail.com")
        .role(RoleEnum.ADMIN)
        .isActive(true)
        .createdAt(LocalDateTime.now(ZoneOffset.UTC))
        .updatedAt(LocalDateTime.now(ZoneOffset.UTC))
        .build();
  }

  public static PasswordModel getPasswordModel() {
    return PasswordModel.builder()
        .documentId("765892644")
        .oldPassword("dead252003")
        .newPassword("kami369852")
        .confirmPassword("kami369852")
        .build();
  }
  public static PasswordUpdateRequest getPasswordUpdateRequest() {
    return PasswordUpdateRequest.builder()
        .oldPassword("dead252003")
        .newPassword("kami369852")
        .confirmPassword("kami369852")
        .build();
  }
}
