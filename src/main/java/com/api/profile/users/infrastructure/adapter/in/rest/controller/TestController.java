package com.api.profile.users.infrastructure.adapter.in.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing test operations.
 */
@RestController
@RequestMapping("api/v0/test")
public class TestController {

  /**
   * endpoint to testing app.
   */
  @GetMapping("/helloWorld")
  public ResponseEntity<String> helloWord() {
    return new ResponseEntity<>("Hola Mundo!", HttpStatus.OK);
  }
}
