package com.group3.fams.DTO.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateTrainingProgramRequest {
    @NotNull
    String name;

    @NotNull
    LocalDate startTime;

    @NotNull
    Integer duration;

    @NotNull
    String topicCode;

    @NotNull
    String status;

    @NotNull
    Integer user;
//    @NotNull
//    String createdBy;
//
//    @NotNull
//    LocalDate createdDate;
}
