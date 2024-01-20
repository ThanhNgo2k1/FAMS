package com.group3.fams.controller;

import com.group3.fams.DTO.request.LoginRequest;
import com.group3.fams.DTO.response.ResponseObject;
import com.group3.fams.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<ResponseObject> login(@RequestBody LoginRequest loginRequest) {
    return authService.login(loginRequest);
  }

  @PostMapping("/logout")
  public ResponseEntity<ResponseObject> logout(@RequestHeader String authorization) {
    return authService.logout(authorization);
  }
}
