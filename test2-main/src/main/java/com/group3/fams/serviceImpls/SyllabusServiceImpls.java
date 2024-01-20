package com.group3.fams.serviceImpls;

import com.group3.fams.DTO.request.CreateSyllabusRequest;
import com.group3.fams.DTO.request.UpdateSyllabusRequest;
import com.group3.fams.DTO.response.ResponseObject;
import com.group3.fams.DTO.response.SyllabusResponse;
import com.group3.fams.DTO.response.TrainingContentResponse;
import com.group3.fams.DTO.response.TrainingUnitResponse;
import com.group3.fams.Mapper.SyllabusMapper;
import com.group3.fams.entity.Syllabus;
import com.group3.fams.entity.TrainingContent;
import com.group3.fams.entity.TrainingUnit;
import com.group3.fams.repositories.SyllabusRepository;
import com.group3.fams.repositories.TrainingUnitRepository;
import com.group3.fams.repositories.UserRepository;
import com.group3.fams.service.SyllabusService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class SyllabusServiceImpls implements SyllabusService {

  final SyllabusRepository syllabusRepository;
  final UserRepository userRepository;
  final SyllabusMapper syllabusMapper;
  final TrainingUnitRepository trainingUnitRepository;

  @Override
  public ResponseEntity<List<TrainingUnitResponse>> loadTrainingUnit(String dayNumber) throws Exception {
    List<TrainingUnit> trainingUnits = trainingUnitRepository.findByDayNumber(dayNumber);
    List<TrainingUnitResponse> list = new ArrayList<>();
    if (trainingUnits != null  && !trainingUnits.isEmpty()) {
      for (TrainingUnit trainingUnit : trainingUnits) {
        ArrayList<TrainingContentResponse> listTC = new ArrayList<>();
        listTC.add(TrainingContentResponse
            .builder()
                .trainingContentName(trainingUnit.getUnitName())
                .outputStandard(trainingUnit.getTrainingContent().getOutputStandards().getOutputStandardName())
                .deliveryType(trainingUnit.getTrainingContent().getDeliveryType())
                .duration(trainingUnit.getTrainingContent().getDuration())
            .build());
          list.add(
              TrainingUnitResponse
                  .builder()
                  .message("Lor")
                  .status("200")
                  .dayNumber("Day " + trainingUnit.getDayNumber())
                  .unitCode(trainingUnit.getTrainingContent().getUnitCode())
                  .unitName(trainingUnit.getUnitName())
                  .trainingContent(listTC)
                  .build());
      }
      return ResponseEntity.ok().body(list);
    }
    return ResponseEntity.badRequest().body(null);
  }

  @Override
  public ResponseEntity<ResponseObject> loadSyllabus(int pageNum, int pageSize) throws Exception {
    Pageable paging = PageRequest.of(pageNum, pageSize);
    Page<Syllabus> syllabus = syllabusRepository.findAll(paging);

    List<Syllabus> list = syllabus.getContent();

    if (syllabus != null  && !syllabus.isEmpty()) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(
              ResponseObject
                  .builder()
                  .message("Load Successful")
                  .status("200")
                  .payload(list)
                  .build());
    }
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(
            ResponseObject
                .builder()
                .message("Load syllabus failed!")
                .status("403")
                .payload(null)
                .build());
  }

  @Override
  public ResponseEntity<ResponseObject> createSyllabus(
      CreateSyllabusRequest createSyllabusRequest, BindingResult bindingResult) throws Exception {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userEmail = authentication.getName();

    if (bindingResult.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(ResponseObject.builder().message("Create failed").status("400").build());
    }

    Syllabus newSyllabus =
        SyllabusMapper.INSTANCE.createSyllabusRequestToSyllabus(createSyllabusRequest);
    newSyllabus.setCreatedBy(userEmail);
    syllabusRepository.save(newSyllabus);

    return ResponseEntity.status(HttpStatus.OK)
        .body(
            ResponseObject.builder()
                .message("Create Successful")
                .status("200")
                .payload(newSyllabus)
                .build());
  }

  @Override
  public ResponseEntity<ResponseObject> updateSyllabus(
      UpdateSyllabusRequest updateSyllabusRequest, int id, BindingResult bindingResult)
      throws Exception {
    Syllabus existingSyllabus = syllabusRepository.findById(id).orElse(null);

    if (existingSyllabus != null && !bindingResult.hasErrors()) {

      Syllabus updatedSyllabus =
          SyllabusMapper.INSTANCE.updateSyllabusRequestToSyllabus(
              updateSyllabusRequest, existingSyllabus);
      syllabusRepository.save(updatedSyllabus);

      return ResponseEntity.status(HttpStatus.OK)
          .body(
              ResponseObject.builder()
                  .message("Super_Admin Update Successful")
                  .status("200")
                  .payload(updatedSyllabus)
                  .build());
    }

    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(
            ResponseObject.builder()
                .message("Update failed")
                .status("403")
                .payload(existingSyllabus)
                .build());
  }

  @Override
  public ResponseEntity<ResponseObject> searchSyllabus(String topicName, int pageNum,
      int pageSize) throws Exception {
    Pageable paging = PageRequest.of(pageNum, pageSize);
    Page<Syllabus> listSyllabus =
        syllabusRepository.findAllByTopicNameContainingIgnoreCase(topicName, paging);
    if (listSyllabus != null && !listSyllabus.isEmpty()) {
      List<SyllabusResponse> list = new ArrayList<>();
      for (Syllabus syllabus : listSyllabus) {
        list.add(
            SyllabusResponse.builder()
                .topicCode(syllabus.getTopicCode())
                .topicName(syllabus.getTopicName())
                .technicalGroup(syllabus.getTechnicalGroup())
                .version(syllabus.getVersion())
                .trainingAudience(syllabus.getTrainingAudience())
                .topicOutline(syllabus.getTopicOutline())
                .trainingMaterials(syllabus.getTrainingMaterials())
                .trainingPrinciples(syllabus.getTrainingPrinciples())
                .priority(syllabus.getPriority())
                .createdBy(syllabus.getCreatedBy())
                .createdDate(syllabus.getCreatedDate())
                .modifiedBy(syllabus.getModifiedBy())
                .modifiedDate(syllabus.getModifiedDate())
                .trainingUnits(syllabus.getTrainingAudience())
                .trainingProgramSet(syllabus.getTrainingAudience())
                .learningObjectiveSet(syllabus.getCreatedBy())
                .build());
      }
      long totalSyllabus = listSyllabus.getTotalElements();
      int count = list.size();
      return ResponseEntity.ok().body(ResponseObject
          .builder()
          .message("Found " + count + "/" + totalSyllabus + "   Syllabus")
          .status("200")
          .payload(list)
          .build());
    }
    return null;
  }

  @Override
  public ResponseEntity<ResponseObject> importSyllabusFromCSVFile(MultipartFile csvFile)
      throws IOException, CsvValidationException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userEmail = authentication.getName();

    List<Syllabus> syllabusList = new ArrayList<>();
    List<String> errors = new ArrayList<>();

    if (!csvFile.getOriginalFilename().contains(".csv")) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject
          .builder()
          .message("Wrong format! Only CSV file accepted.")
          .status("400")
          .payload(null)
          .build());
    }

    if (csvFile.isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject
          .builder()
          .message("File is empty!")
          .status("400")
          .payload(null)
          .build());
    }

    try (CSVReader reader = new CSVReader(new InputStreamReader(csvFile.getInputStream()))) {
      String[] header = reader.readNext();
      String[] row;
      List<Syllabus> list = syllabusRepository.findAll();
      int line = 1;
      while ((row = reader.readNext()) != null) {
        line++;

        if (row.length != header.length) {
          errors.add("Incorrect number of columns");
          continue;
        }

        if (row[0].isEmpty() || row[1].isEmpty() || row[2].isEmpty() || row[3].isEmpty()
            || row[4].isEmpty()
            || row[5].isEmpty() || row[6].isEmpty() || row[7].isEmpty() || row[8].isEmpty()) {
          errors.add("Missing data at line [" + line + "]");
          continue;
        }

        for (Syllabus syl : list) {
          if (syl.getTopicName().equals(row[0]) && syl.getVersion().equals(row[2])) {
            errors.add("Duplicated data at line [" + line + "]");
            break;
          }
        }

        Syllabus syllabus = new Syllabus();
        syllabus.setTopicName(row[0]);
        syllabus.setTechnicalGroup(row[1]);
        syllabus.setVersion(row[2]);
        syllabus.setTrainingAudience(row[3]);
        syllabus.setTopicOutline(row[4]);
        syllabus.setTrainingMaterials(row[5]);
        syllabus.setTrainingPrinciples(row[6]);
        if (!row[7].matches("^\\d+$")) {
          errors.add(
              ("Invalid data at line [" + line + "]: Priority must be a non-negative integer"));
          continue;
        } else {
          syllabus.setPriority(Integer.parseInt(row[7]));
        }
        syllabus.setCreatedBy(userEmail);
        syllabus.setCreatedDate(LocalDateTime.now());
        syllabus.setPublishStatus(row[8]);
        syllabusList.add(syllabus);
      }
    }
    if (!errors.isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject
          .builder()
          .message("CSV data validation failed!")
          .status("400")
          .payload(errors)
          .build());
    }
    syllabusRepository.saveAll(syllabusList);

    return ResponseEntity.status(HttpStatus.OK).body(ResponseObject
        .builder()
        .message("Imported syllabus data successfully!")
        .status("200")
        .payload(syllabusList)
        .build());
  }

  @Override
  public ResponseEntity<ResponseObject> duplicateSyllabus(int id) {
    Syllabus originalSyllabus = syllabusRepository.findById(id).orElse(null);
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userEmail = authentication.getName();
    if (originalSyllabus != null) {
      Syllabus duplicateSyllabus = new Syllabus(originalSyllabus);
      duplicateSyllabus.setTopicName(duplicateSyllabus.getTopicName() + "_duplicate");
      duplicateSyllabus.setCreatedBy(userEmail);
      duplicateSyllabus.setCreatedDate(LocalDateTime.now());
      syllabusRepository.save(duplicateSyllabus);
      return ResponseEntity.status(HttpStatus.OK)
          .body(ResponseObject.builder()
              .message("Duplicate Successful")
              .status("200")
              .payload(duplicateSyllabus)
              .build());
    }
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(ResponseObject.builder()
            .message("Duplicate failed")
            .status("403")
            .payload(null)
            .build());
  }
}
