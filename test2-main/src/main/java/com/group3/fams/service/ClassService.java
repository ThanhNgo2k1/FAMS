package com.group3.fams.service;

import com.group3.fams.DTO.request.CreateClassRequest;
import com.group3.fams.DTO.request.DeleteClassRequest;
import com.group3.fams.DTO.request.UpdateClassRequest;
import com.group3.fams.DTO.response.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface ClassService {

  ResponseEntity<ResponseObject> getAllClass();

  ResponseEntity<ResponseObject> createClass(
      CreateClassRequest createClassRequest, BindingResult bindingResult);

  ResponseEntity<ResponseObject> updateClass(
      UpdateClassRequest updateClassRequest, int id, BindingResult bindingResult);

  ResponseEntity<ResponseObject> deleteClass(
      DeleteClassRequest deleteClassRequest, int id, BindingResult bindingResult);
}
