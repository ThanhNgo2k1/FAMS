package com.group3.fams.serviceImpls;

import com.group3.fams.DTO.request.CreateTrainingProgramRequest;
import com.group3.fams.DTO.request.DeleteTrainingProgramRequest;
import com.group3.fams.DTO.request.UpdateTrainingProgramRequest;
import com.group3.fams.DTO.response.ResponseObject;
import com.group3.fams.Mapper.TrainingProgramMaper;
import com.group3.fams.entity.TrainingProgram;
import com.group3.fams.repositories.TrainingProgramRepository;
import com.group3.fams.repositories.UserRepository;
import com.group3.fams.service.TrainingProgramService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
public class TrainingProgramImpls implements TrainingProgramService {

    private final TrainingProgramRepository trainingProgramRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<ResponseObject> getAllTrainingProgram() {
        List<TrainingProgram> trainingPrograms = trainingProgramRepository.findAll();
        if (trainingPrograms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseObject.builder().message("Empty").status("403").payload(null).build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ResponseObject.builder()
                                .message("done")
                                .status("200")
                                .payload(trainingPrograms)
                                .build());
    }

    @Override
    public ResponseEntity<ResponseObject> createTrainingProgram(
            CreateTrainingProgramRequest createTrainingProgramRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseObject.builder().message("create failed").status("403").build());
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        TrainingProgram newTrainingProgram =
                TrainingProgramMaper.INSTANCE.createTrainingProgramRequestToTrainingProgram(
                        createTrainingProgramRequest);
        newTrainingProgram.setUser(
                TrainingProgramMaper.INSTANCE.map(createTrainingProgramRequest.getUser()));
        newTrainingProgram.setCreatedBy(userEmail);
        newTrainingProgram.setCreatedDate(LocalDateTime.now());

        trainingProgramRepository.save(newTrainingProgram);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ResponseObject.builder()
                                .message("Create Successful")
                                .status("200")
                                .payload(newTrainingProgram)
                                .build());
    }

    @Override
    public ResponseEntity<ResponseObject> updateTrainingProgram(
            UpdateTrainingProgramRequest updateTrainingProgramRequest,
            int id,
            BindingResult bindingResult) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        TrainingProgram optionalTrainingProgram = trainingProgramRepository.findById(id).orElse(null);

        if (optionalTrainingProgram != null && !bindingResult.hasErrors()) {
            TrainingProgram updatedTrainingProgram =
                    TrainingProgramMaper.INSTANCE.updateTrainingProgramRequestToTrainingProgram(
                            updateTrainingProgramRequest, optionalTrainingProgram);
            updatedTrainingProgram.setModifiedBy(userEmail);
            updatedTrainingProgram.setModifiedDate(LocalDateTime.now());
            trainingProgramRepository.save(updatedTrainingProgram);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            ResponseObject.builder()
                                    .message("Update Successful")
                                    .status("200")
                                    .payload(updatedTrainingProgram)
                                    .build());
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                        ResponseObject.builder()
                                .message("Update failed")
                                .status("403")
                                .payload(optionalTrainingProgram)
                                .build());
    }

    @Override
    public ResponseEntity<ResponseObject> deleteTrainingProgram(
            DeleteTrainingProgramRequest deleteTrainingProgramRequest,
            int id,
            BindingResult bindingResult) {

        TrainingProgram optionalTrainingProgram = trainingProgramRepository.findById(id).orElse(null);

        if (optionalTrainingProgram != null && !bindingResult.hasErrors()) {

            String requestedStatus = deleteTrainingProgramRequest.getStatus().toLowerCase();

            if (requestedStatus.equals("active")
                    || requestedStatus.equals("unactive")
                    || requestedStatus.equals("draft")) {

                optionalTrainingProgram.setStatus(requestedStatus);
                trainingProgramRepository.save(optionalTrainingProgram);

                return ResponseEntity.status(HttpStatus.OK)
                        .body(
                                ResponseObject.builder()
                                        .message("Update Status Successful")
                                        .status("200")
                                        .payload(optionalTrainingProgram)
                                        .build());
            } else {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(
                                ResponseObject.builder()
                                        .message("Invalid Status")
                                        .status("400")
                                        .payload(null)
                                        .build());
            }
        } else {

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(
                            ResponseObject.builder()
                                    .message("Update Status Failed")
                                    .status("403")
                                    .payload(null)
                                    .build());
        }
    }
}
