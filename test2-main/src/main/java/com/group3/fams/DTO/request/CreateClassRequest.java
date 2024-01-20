package com.group3.fams.DTO.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateClassRequest {

  @NotNull String className;

  @NotNull String classCode;

  @NotNull Integer duration;

  @NotNull String status;

  @NotNull String location;

  @NotNull String fsu;

  @NotNull LocalDate startDate;

  @NotNull LocalDate endDate;
}
