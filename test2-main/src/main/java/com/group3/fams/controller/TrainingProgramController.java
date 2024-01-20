package com.group3.fams.controller;

import com.group3.fams.DTO.request.CreateTrainingProgramRequest;
import com.group3.fams.DTO.request.DeleteTrainingProgramRequest;
import com.group3.fams.DTO.request.UpdateTrainingProgramRequest;
import com.group3.fams.DTO.response.ResponseObject;
import com.group3.fams.service.TrainingProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class TrainingProgramController {

  private final TrainingProgramService trainingProgramService;

  @GetMapping(value = "/trainingprogram/loadAll")
  @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
  ResponseEntity<ResponseObject> getAllTrainingProgram() {
    return trainingProgramService.getAllTrainingProgram();
  }

  @PostMapping("/trainingprogram/createTrainingprogram")
  @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
  ResponseEntity<ResponseObject> createTrainingProgram(
      @RequestBody CreateTrainingProgramRequest createTrainingProgramRequest,
      BindingResult bindingResult) {
    return trainingProgramService.createTrainingProgram(
        createTrainingProgramRequest, bindingResult);
  }

  @PatchMapping("/trainingprogram/updateTrainingprogram/{id}")
  @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
  ResponseEntity<ResponseObject> updateTrainingProgram(
      @RequestBody UpdateTrainingProgramRequest updateTrainingProgramRequest,
      @PathVariable int id,
      BindingResult bindingResult) {
    return trainingProgramService.updateTrainingProgram(
        updateTrainingProgramRequest, id, bindingResult);
  }

  @PatchMapping("/trainingprogram/deleteTrainingprogram/{id}")
  @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
  ResponseEntity<ResponseObject> deleteTrainingProgram(
      @RequestBody DeleteTrainingProgramRequest deleteTrainingProgramRequest,
      @PathVariable int id,
      BindingResult bindingResult) {
    return trainingProgramService.deleteTrainingProgram(
        deleteTrainingProgramRequest, id, bindingResult);
  }
}
