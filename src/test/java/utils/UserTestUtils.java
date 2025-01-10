package utils;

import com.api.profile.users.domain.model.user.DocumentType;
import com.api.profile.users.domain.model.user.RoleEnum;
import com.api.profile.users.domain.model.user.UserModel;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

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
  public static UserModel getInvaliduserModel() {
    return UserModel.builder()
        .name("pedro")
        .lastName("perez")
        .email("pedro.perez@gmail.com")
        .updatedAt(LocalDateTime.now(ZoneOffset.UTC))
        .build();
  }

}
