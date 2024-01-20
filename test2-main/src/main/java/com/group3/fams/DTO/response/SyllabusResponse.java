package com.group3.fams.DTO.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class  SyllabusResponse {

  String message;

  int topicCode;

  String topicName;

  String technicalGroup;

  String version;

  String trainingAudience;

  String topicOutline;

  String trainingMaterials;

  String trainingPrinciples;

  int priority;

  String createdBy;

  LocalDateTime createdDate;

  String modifiedBy;

  LocalDateTime modifiedDate;

  String trainingUnits;

  String trainingProgramSet;

  String learningObjectiveSet;
}
