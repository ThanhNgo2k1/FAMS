package com.group3.fams.DTO.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateSyllabusRequest {

  @NotNull Integer priority;

  @NotNull String publishStatus;

  @NotNull String technicalGroup;

  @NotNull String topicName;

  @NotNull String topicOutline;

  @NotNull String trainingAudience;

  @NotNull String trainingMaterials;

  @NotNull String trainingPrinciples;

  @NotNull String version;
}
