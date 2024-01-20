package com.group3.fams.service;

import com.group3.fams.DTO.request.CreateUserRequest;
import com.group3.fams.DTO.request.DeleteUserRequest;
import com.group3.fams.DTO.request.UpdateUserRequest;
import com.group3.fams.DTO.response.ResponseObject;
import com.group3.fams.DTO.response.UserPermissionResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface UserService {

  ResponseEntity<ResponseObject> createUser(
      CreateUserRequest createUserRequest, BindingResult bindingResult) throws Exception;

  ResponseEntity<ResponseObject> loadUsers(int pageNum, int pageSize) throws Exception;

  ResponseEntity<ResponseObject> updateUser(
      UpdateUserRequest updateUserRequest, int id, BindingResult bindingResult) throws Exception;

  ResponseEntity<ResponseObject> deleteUser(
      DeleteUserRequest deleteUserRequest, int id, BindingResult bindingResult) throws Exception;

  ResponseEntity<List<UserPermissionResponse>> loadUserPermission(int pageNum, int pageSize) throws Exception;
}
