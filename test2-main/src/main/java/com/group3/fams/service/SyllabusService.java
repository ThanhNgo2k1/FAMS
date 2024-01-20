package com.group3.fams.service;

import com.group3.fams.DTO.request.CreateSyllabusRequest;
import com.group3.fams.DTO.request.UpdateSyllabusRequest;
import com.group3.fams.DTO.response.ResponseObject;
import com.group3.fams.DTO.response.TrainingUnitResponse;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

public interface SyllabusService {

  ResponseEntity<List<TrainingUnitResponse>> loadTrainingUnit(String dayNumber) throws Exception;

  ResponseEntity<ResponseObject> loadSyllabus(int pageNum, int  pageSize) throws Exception;

  ResponseEntity<ResponseObject> createSyllabus(
      CreateSyllabusRequest createSyllabusRequest, BindingResult bindingResult) throws Exception;

  ResponseEntity<ResponseObject> updateSyllabus(
      UpdateSyllabusRequest updateSyllabusRequest, int id, BindingResult bindingResult) throws Exception;

  ResponseEntity<ResponseObject> searchSyllabus(String topicName, int pageNum, int pageSize) throws Exception;

  ResponseEntity<ResponseObject> importSyllabusFromCSVFile(
      MultipartFile csvFile) throws IOException, CsvValidationException;

  ResponseEntity<ResponseObject> duplicateSyllabus(int id);
}
