package com.group3.fams.DTO.response;

import com.group3.fams.entity.TrainingContent;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.util.List;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TrainingUnitResponse {

  String message;

  String status;

  String dayNumber;

  Integer unitCode;//khoa chinh cua TrainingContent

  String unitName;// ten cua trainingUnit

  List<TrainingContentResponse> trainingContent;

}
