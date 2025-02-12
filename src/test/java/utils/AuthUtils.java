package utils;

import com.api.profile.users.domain.model.auth.AuthLoginModel;
import com.api.profile.users.domain.model.auth.AuthResponseModel;
import com.api.profile.users.infrastructure.adapter.in.rest.model.request.AuthLoginRequest;
import com.api.profile.users.infrastructure.adapter.in.rest.model.response.AuthResponse;

public class AuthUtils {
  public static AuthResponseModel getAuthResponseModel() {
    return AuthResponseModel.builder()
        .documentId("765892644")
        .message("login successfully")
        .jwt("generatedToken")
        .status(true)
        .build();
  }

  public static AuthResponse getAuthResponse() {
    return AuthResponse.builder()
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

  public static AuthLoginRequest getAuthLoginRequest() {
    return AuthLoginRequest.builder()
        .documentId("765892644")
        .password("dead252003")
        .build();
  }
}
