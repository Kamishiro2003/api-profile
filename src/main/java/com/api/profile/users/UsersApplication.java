package com.api.profile.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the AccountEase application.
 */
@SpringBootApplication
public class UsersApplication {

  /**
   * Starts the application.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {

    SpringApplication.run(UsersApplication.class, args);
  }

}
