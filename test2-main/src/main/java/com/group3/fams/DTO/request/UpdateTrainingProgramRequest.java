package com.group3.fams.DTO.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateTrainingProgramRequest {

  @NotNull String name;

  @NotNull LocalDateTime startTime;

  @NotNull Integer duration;

  @NotNull String topicCode;

  @NotNull String status;

  @NotNull Integer user;
}
