package com.group3.fams.Mapper;

import com.group3.fams.DTO.request.CreateTrainingProgramRequest;
import com.group3.fams.DTO.request.UpdateTrainingProgramRequest;
import com.group3.fams.entity.TrainingProgram;
import com.group3.fams.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface TrainingProgramMaper {

  TrainingProgramMaper INSTANCE = Mappers.getMapper(TrainingProgramMaper.class);

  default User map(Integer userId) {
    if (userId == null) {
      return null;
    }
    User user = new User();
    user.setUserId(userId);
    return user;
  }

  @Mapping(source = "name", target = "name")
  @Mapping(source = "duration", target = "duration")
  @Mapping(source = "topicCode", target = "topicCode")
  @Mapping(target = "status", constant = "active")
  @Mapping(source = "startTime", target = "startTime")
  TrainingProgram createTrainingProgramRequestToTrainingProgram(
      CreateTrainingProgramRequest createTrainingProgramRequest);

  @Mapping(source = "name", target = "name")
  @Mapping(source = "duration", target = "duration")
  @Mapping(source = "topicCode", target = "topicCode")
  @Mapping(source = "status", target = "status")
  @Mapping(source = "startTime", target = "startTime")
  TrainingProgram updateTrainingProgramRequestToTrainingProgram(
      UpdateTrainingProgramRequest updateTrainingProgramRequest,
      @MappingTarget TrainingProgram trainingProgram);
}
