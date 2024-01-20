package com.group3.fams.DTO.response;

import com.group3.fams.Enum.PermissionManagement;
import com.group3.fams.Enum.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserPermissionResponse {

  Integer permissionId;

  PermissionManagement classManagement;

  PermissionManagement learningMaterialManagement;

  Role role;

  PermissionManagement syllabusManagement;

  PermissionManagement trainingProgramManagement;

  PermissionManagement userManagement;

  List<UserResponse> user;
}
