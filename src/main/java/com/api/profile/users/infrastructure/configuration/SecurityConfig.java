package com.api.profile.users.infrastructure.configuration;

import com.api.profile.users.application.usecase.user.UserDetailServiceImpl;
import com.api.profile.users.domain.model.user.RoleEnum;
import com.api.profile.users.infrastructure.configuration.filter.JwtTokenValidator;
import com.api.profile.users.util.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Security settings of the application, including stateless session management, JWT-based request
 * filtering, and password encoding.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtUtils jwtUtils;

  /**
   * Configures the main security filter chain with JWT token validation, stateless session
   * management, and endpoint access rules.
   *
   * @param httpSecurity           the HTTP security configuration
   * @param authenticationProvider the authentication provider for handling user authentication
   * @return a configured {@link SecurityFilterChain}
   * @throws Exception if an error occurs during configuration
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
      AuthenticationProvider authenticationProvider
  ) throws Exception {

    return httpSecurity.csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .authorizeHttpRequests(http -> {
          // Public endpoints
          http.requestMatchers(HttpMethod.GET, "api/v0/test/helloWorld").permitAll();

          // Private endpoints
          http.requestMatchers("/api/v1/users").permitAll();
          http.requestMatchers("/api/v1/**").hasRole(RoleEnum.ADMIN.toString());

          http.anyRequest().denyAll();
        })
        .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
        .build();
  }

  /**
   * Provides the authentication manager bean used by Spring Security.
   *
   * @param authenticationConfiguration the authentication configuration
   * @return an {@link AuthenticationManager} instance
   * @throws Exception if an error occurs retrieving the authentication manager
   */
  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration
  ) throws Exception {

    return authenticationConfiguration.getAuthenticationManager();
  }

  /**
   * Configures an authentication provider using the application's user details service and a BCrypt
   * password encoder.
   *
   * @param userDetailService the custom user details service
   * @return a configured {@link AuthenticationProvider} instance
   */
  @Bean
  public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService) {

    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder());
    provider.setUserDetailsService(userDetailService);
    return provider;
  }

  /**
   * Define the password encoder to use for encoding the user password.
   *
   * @return a {@link PasswordEncoder} instance with BCrypt encoding
   */
  @Bean
  public PasswordEncoder passwordEncoder() {

    return new BCryptPasswordEncoder();
  }
}
