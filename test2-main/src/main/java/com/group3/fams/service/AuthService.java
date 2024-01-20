package com.group3.fams.service;

import com.group3.fams.DTO.request.LoginRequest;
import com.group3.fams.DTO.response.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface AuthService {

  ResponseEntity<ResponseObject> login(LoginRequest loginRequest);

  ResponseEntity<ResponseObject> logout(String token);
}
