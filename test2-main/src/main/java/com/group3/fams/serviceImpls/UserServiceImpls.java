package com.group3.fams.serviceImpls;

import com.group3.fams.DTO.request.CreateUserRequest;
import com.group3.fams.DTO.request.DeleteUserRequest;
import com.group3.fams.DTO.request.UpdateUserRequest;
import com.group3.fams.DTO.response.ResponseObject;
import com.group3.fams.DTO.response.UserPermissionResponse;
import com.group3.fams.DTO.response.UserResponse;
import com.group3.fams.Enum.Role;
import com.group3.fams.Mapper.UserMapper;
import com.group3.fams.entity.User;
import com.group3.fams.entity.UserPermission;
import com.group3.fams.repositories.UserPermissionRepository;
import com.group3.fams.repositories.UserRepository;
import com.group3.fams.service.UserService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
public class UserServiceImpls implements UserService {


  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final UserPermissionRepository userPermissionRepository;

  @Override
  public ResponseEntity<ResponseObject> createUser(CreateUserRequest createUserRequest,
      BindingResult bindingResult) throws Exception {
    if (!bindingResult.hasErrors()) {
      User newUser = User
          .builder()
          .email(createUserRequest.getEmail())
          .password(passwordEncoder.encode(createUserRequest.getPassword()))
          .userPermission(
              userPermissionRepository.findById(createUserRequest.getUserPermission()).get())
          .build();
      userRepository.save(newUser);

      return ResponseEntity.status(HttpStatus.OK).body(ResponseObject
          .builder()
          .message("Create Successful")
          .status("200")
          .payload(newUser)
          .build());
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject
        .builder()
        .message("create failed")
        .status("500")
        .build());
  }

  @Override
  public ResponseEntity<ResponseObject> loadUsers(int pageNum, int pageSize) throws Exception {
    Pageable paging = PageRequest.of(pageNum, pageSize);
    Page<User> user = userRepository.findAll(paging);

    if (user != null) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(
              ResponseObject
                  .builder()
                  .message("Load Successful")
                  .status("200")
                  .payload(user)
                  .build());
    }
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(
            ResponseObject
                .builder()
                .message("Load User successful")
                .status("403")
                .payload(null)
                .build());
  }

  @Override
  public ResponseEntity<ResponseObject> updateUser(UpdateUserRequest updateUserRequest, int id,
      BindingResult bindingResult) throws Exception {
    User optionalUser = userRepository.findById(id).orElse(null);

    if (optionalUser != null && !bindingResult.hasErrors()) {

      User updatedUser = UserMapper.INSTANCE.updateUserRequestToUser(updateUserRequest,
          optionalUser);
      updatedUser.setModifiedDate(LocalDate.now());
      optionalUser.setUserPermission(
          userPermissionRepository.findById(updateUserRequest.getUserPermission()).get());

      userRepository.save(updatedUser);

      return ResponseEntity.status(HttpStatus.OK).body(ResponseObject
          .builder()
          .message("User Update Successful")
          .status("200")
          .payload(updatedUser)
          .build());
    }

    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseObject
        .builder()
        .message("Update failed")
        .status("403")
        .payload(optionalUser)
        .build());
  }

  private boolean hasPermissionToUpdateUserPermission(String userEmail) throws Exception {
    User user = userRepository.findUserByEmail(userEmail).orElse(null);
    return user != null && user.getUserPermission().getRole() == Role.SUPER_ADMIN;
  }


  @Override
  public ResponseEntity<ResponseObject> deleteUser(DeleteUserRequest deleteUserRequest, int id,
      BindingResult bindingResult) throws Exception {
    User optionalUser = userRepository.findById(id).get();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userEmail = authentication.getName();

    if (optionalUser.getUserPermission().getRole() != Role.SUPER_ADMIN) {
      if (optionalUser != null && !bindingResult.hasErrors() && hasPermissionToUpdateUserPermission(
          userEmail)) {
        optionalUser.setStatus("0");
        userRepository.save(optionalUser);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseObject
            .builder()
            .message("SuperAdmin de-active successful")
            .status("200")
            .payload(optionalUser)
            .build());
      } else if (!hasPermissionToUpdateUserPermission(userEmail)
          && optionalUser.getUserPermission().getRole() == Role.TRAINEE) {
        optionalUser.setStatus("0");
        userRepository.save(optionalUser);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseObject
            .builder()
            .message("Admin de-active trainee successful")
            .status("200")
            .payload(optionalUser)
            .build());
      }
    }
    return ResponseEntity.status(HttpStatus.OK).body(ResponseObject
        .builder()
        .message("Cant de-active user")
        .status("403")
        .build());
  }


  @Override
  public ResponseEntity<List<UserPermissionResponse>> loadUserPermission(int pageNum, int pageSize)
      throws Exception {
    Pageable paging = PageRequest.of(pageNum, pageSize);
    Page<UserPermission> userPermissions = userPermissionRepository.findAll(paging);//list of role
    List<UserPermissionResponse> list = new ArrayList<>();
    //list of user
    if (userPermissions != null && !userPermissions.isEmpty()) {
      for (UserPermission up : userPermissions) {
        list.add(
            UserPermissionResponse
                .builder()
                .permissionId(up.getPermissionId())
                .classManagement(up.getClassManagement())
                .learningMaterialManagement(up.getLearningMaterialManagement())
                .role(up.getRole())
                .syllabusManagement(up.getSyllabusManagement())
                .trainingProgramManagement(up.getTrainingProgramManagement())
                .userManagement(up.getUserManagement())
                .user(getUserByRole(up.getRole()))
                .build()
        );
      }
      return ResponseEntity
          .ok()
          .body(list);
    }

    return ResponseEntity
        .badRequest()
        .body(null);
  }


  private List<UserResponse> getUserByRole(Role role) throws Exception {
    List<User> listUser = userRepository.findAll();
    List<UserResponse> listU = new ArrayList<>();

    for (User u : listUser) {
      if (u.getUserPermission().getRole().equals(role)) {
        listU.add(
            UserResponse
                .builder()
                .id(u.getUserId())
                .email(u.getEmail())
                .build()
        );
      }
    }
    return listU;
  }

}


