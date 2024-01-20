package com.group3.fams.serviceImpls;

import com.group3.fams.DTO.request.LoginRequest;
import com.group3.fams.DTO.response.ResponseObject;
import com.group3.fams.entity.InvalidToken;
import com.group3.fams.entity.User;
import com.group3.fams.repositories.InvalidTokenRepository;
import com.group3.fams.repositories.UserRepository;
import com.group3.fams.service.AuthService;
import com.group3.fams.service.UserService;
import io.jsonwebtoken.Claims;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpls implements AuthService {

  private final UserRepository userRepository;

  private final AuthenticationManager authenticationManager;

  private final JwtServiceImpls jwtServiceImpls;

  private final PasswordEncoder passwordEncoder;

  private final UserService userService;

  @Autowired InvalidTokenRepository invalidTokenRepository;

  public ResponseEntity<ResponseObject> login(LoginRequest loginRequest) {
    User user = userRepository.findUserByEmail(loginRequest.getEmail()).get();
    if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
      String token = jwtServiceImpls.generateToken(user);
      Claims claims = jwtServiceImpls.extractAllClaim(token);
      List<GrantedAuthority> roles = (List<GrantedAuthority>) claims.get("roles");
      //      GrantedAuthority role = (GrantedAuthority) claims.get
      return ResponseEntity.status(HttpStatus.OK)
          .body(
              ResponseObject.builder()
                  .message("Login Successful")
                  .status("200")
                  .payload(token)
                  .build());
    }
    return ResponseEntity.status(HttpStatus.OK)
        .body(ResponseObject.builder().message("Login Failed").status("400").build());
  }

  @Override
  public ResponseEntity<ResponseObject> logout(String token) {
    if (invalidTokenRepository.existsByToken(token)) {
      return ResponseEntity.status(HttpStatus.valueOf(409))
          .body(
              ResponseObject.builder()
                  .message("Duplicate Token found!")
                  .status("409")
                  .payload(token)
                  .build());
    }
    InvalidToken invalidToken = new InvalidToken();
    invalidToken.setToken(token.substring(7));
    invalidTokenRepository.save(invalidToken);
    return ResponseEntity.status(HttpStatus.valueOf(200))
        .body(ResponseObject.builder().message("Logged out.").status("200").payload(null).build());
  }
}
