package com.group3.fams.DTO.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {

  @NotNull
  @NotEmpty
  @Size(min = 1, max = 20)
  String name;

  @NotNull String phone;

  @NotNull String gender;

  @NotNull String email;

  @NotNull LocalDate dob;

  @NotNull String modifiedBy;

  Integer userPermission;
}
