package com.group3.fams.service;

import com.group3.fams.DTO.request.CreateTrainingProgramRequest;
import com.group3.fams.DTO.request.DeleteTrainingProgramRequest;
import com.group3.fams.DTO.request.UpdateTrainingProgramRequest;
import com.group3.fams.DTO.response.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface TrainingProgramService {

  ResponseEntity<ResponseObject> getAllTrainingProgram();

  ResponseEntity<ResponseObject> createTrainingProgram(
      CreateTrainingProgramRequest createTrainingProgramRequest, BindingResult bindingResult);

  ResponseEntity<ResponseObject> updateTrainingProgram(
      UpdateTrainingProgramRequest updateTrainingProgramRequest,
      int id,
      BindingResult bindingResult);

  ResponseEntity<ResponseObject> deleteTrainingProgram(
      DeleteTrainingProgramRequest deleteTrainingProgramRequest,
      int id,
      BindingResult bindingResult);
}
