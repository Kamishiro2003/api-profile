package utils;

import com.api.profile.users.domain.model.auth.AuthLoginModel;
import com.api.profile.users.domain.model.auth.AuthResponseModel;

public class AuthUtils {
  public static AuthResponseModel getAuthResponseModel() {
    return AuthResponseModel.builder()
        .documentId("765892644")
        .message("login successfully")
        .jwt("generatedToken")
        .status(true)
        .build();
  }

  public static AuthLoginModel getAuthLoginModel() {
    return AuthLoginModel.builder()
        .documentId("765892644")
        .password("dead252003")
        .build();
  }
}
