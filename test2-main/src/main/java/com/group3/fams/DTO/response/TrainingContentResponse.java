package com.group3.fams.DTO.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TrainingContentResponse {

  String trainingContentName;

  String outputStandard;

  int duration;
  
  String deliveryType;

}
