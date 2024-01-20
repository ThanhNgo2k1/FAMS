package com.group3.fams.controller;

import com.group3.fams.DTO.request.CreateSyllabusRequest;
import com.group3.fams.DTO.request.UpdateSyllabusRequest;
import com.group3.fams.DTO.response.ResponseObject;
import com.group3.fams.DTO.response.TrainingUnitResponse;
import com.group3.fams.service.SyllabusService;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class SyllabusController {

  private final SyllabusService syllabusService;


  @GetMapping("/loadTrainingUnit")
  @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
  public  ResponseEntity<List<TrainingUnitResponse>> loadTrainingUnit(@RequestParam String dayNumber) throws Exception {
    return syllabusService.loadTrainingUnit(dayNumber);
  }


  @GetMapping("/loadSyllabus")
  @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
  public ResponseEntity<ResponseObject> loadSyllabus(@RequestParam int pageNum,@RequestParam(defaultValue = "2") int pageSize) throws Exception {
    return syllabusService.loadSyllabus(pageNum,  pageSize);
  }

  @PostMapping("/createSyllabus")
  @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN') or hasAuthority('TRAINER')")
  public ResponseEntity<ResponseObject> createSyllabus(
      @Valid @RequestBody CreateSyllabusRequest createSyllabusRequest,
      BindingResult bindingResult) throws Exception{
    return syllabusService.createSyllabus(createSyllabusRequest, bindingResult);
  }

  @PatchMapping("/updateSyllabus/{id}")
  @PreAuthorize(
      "hasAuthority('SUPER_ADMIN') or (hasAuthority('ADMIN')) or (hasAuthority('TRAINER'))")
  public ResponseEntity<ResponseObject> updateUser(
      @Valid @RequestBody UpdateSyllabusRequest updateSyllabusRequest,
      @PathVariable int id,
      BindingResult bindingResult) throws Exception{
    return syllabusService.updateSyllabus(updateSyllabusRequest, id, bindingResult);
  }

  @GetMapping("/searchSyllabus")
  @PreAuthorize(
      "hasAuthority('SUPER_ADMIN') or (hasAuthority('ADMIN')) or (hasAuthority('TRAINER'))")
  public ResponseEntity<ResponseObject> searchSyllabus(
      @RequestParam String topicName, @RequestParam int pageNum,@RequestParam(defaultValue = "2") int pageSize) throws Exception{
    return syllabusService.searchSyllabus(topicName, pageNum, pageSize);
  }

  @PatchMapping("/duplicateSyllabus/{id}")
  @PreAuthorize("hasAuthority('SUPER_ADMIN') or (hasAuthority('ADMIN'))")
  public ResponseEntity<ResponseObject> duplicateSyllabus(@PathVariable int id) {
    return syllabusService.duplicateSyllabus(id);
  }

  @PostMapping("/importSyllabus")
  @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
  public ResponseEntity<ResponseObject> importSyllabusFromCSVFile(@RequestBody MultipartFile csvFile) throws IOException, CsvValidationException {
    return syllabusService.importSyllabusFromCSVFile(csvFile);
  }
}
