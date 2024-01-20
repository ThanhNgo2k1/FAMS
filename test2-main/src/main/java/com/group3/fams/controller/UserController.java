package com.group3.fams.controller;

import com.group3.fams.DTO.request.CreateUserRequest;
import com.group3.fams.DTO.request.DeleteUserRequest;
import com.group3.fams.DTO.request.UpdateUserRequest;
import com.group3.fams.DTO.response.ResponseObject;
import com.group3.fams.DTO.response.UserPermissionResponse;
import com.group3.fams.entity.EmailDetails;
import com.group3.fams.service.EmailService;
import com.group3.fams.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserController {

  private final UserService userService;
  private final EmailService emailService;

  @PostMapping(value = "/createUser")
  @PreAuthorize("hasAuthority('SUPER_ADMIN')")
  public ResponseEntity<ResponseObject> createUser(
      @RequestBody CreateUserRequest createUserRequest, BindingResult bindingResult) throws Exception{
    return userService.createUser(createUserRequest, bindingResult);
  }

  @GetMapping("/loadUsers")
  @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
  public ResponseEntity<ResponseObject> loadAllUsers(@RequestParam int pageNum,@RequestParam(defaultValue = "2") int pageSize) throws Exception{
    return userService.loadUsers(pageNum, pageSize);
  }

  @GetMapping("/loadUserPermission")
  @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
  public ResponseEntity<List<UserPermissionResponse>> loadUserPermission(
      @RequestParam int pageNum, @RequestParam(defaultValue = "2") int pageSize) throws Exception{
    return userService.loadUserPermission(pageNum, pageSize);
  }

  @PatchMapping("/update/{id}")
  //    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
  @PreAuthorize(
      "hasAuthority('SUPER_ADMIN') or (hasAuthority('ADMIN') and !T(java.util.Objects).equals(#updateUserRequest.getUserPermission(), null))")
  public ResponseEntity<ResponseObject> updateUser(
      @Valid @RequestBody UpdateUserRequest updateUserRequest,
      @PathVariable int id,
      BindingResult bindingResult) throws Exception{
    return userService.updateUser(updateUserRequest, id, bindingResult);
  }

  @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
  @PatchMapping("/delete/{id}")
  public ResponseEntity<ResponseObject> deleteUser(
      @RequestBody DeleteUserRequest deleteUserRequest,
      @PathVariable int id,
      BindingResult bindingResult) throws Exception{
    return userService.deleteUser(deleteUserRequest, id, bindingResult);
  }
  @PostMapping(value = "/sendNewPasswordEmail")
  public ResponseEntity<String> sendNewPasswordEmail(
          @RequestBody EmailDetails emailDetails) {
    String result = emailService.sendMailtoNewPassword(emailDetails);
    if ("Mail sent successfully".equals(result)) {
      return ResponseEntity.ok("Email mật khẩu mới đã được gửi thành công");
    } else {
      return ResponseEntity.badRequest().body("Gửi email mật khẩu mới đã thất bại" + result);
    }
  }
  @PostMapping(value = "/sendPasswordChangeEmail")
  public ResponseEntity<String> sendPasswordChangeEmail(
          @RequestBody EmailDetails emailDetails) {
    String result = emailService.sendMailtoChangePassword(emailDetails);
    if ("Mail sent successfully".equals(result)) {
      return ResponseEntity.ok("Email thay đổi mật khẩu đã được gửi thành công");
    } else {
      return ResponseEntity.badRequest().body("Gửi email thay đổi mật khẩu đã thất bại" + result);
    }
  }

}
