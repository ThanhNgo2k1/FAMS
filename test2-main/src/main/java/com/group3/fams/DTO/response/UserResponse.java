package com.group3.fams.DTO.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserResponse {

  int id;

  String email;

  //  String phone;
  //
  //  String status;
  //
  //  String email;
  //
  //  String gender;
  //
  //  LocalDate dob;
  //
  //  String createdBy;
  //
  //  LocalDate createdDate;
  //
  //  String modifiedBy;
  //
  //  LocalDate modifiedDate;
  //
  //  Role userPermission;

}
