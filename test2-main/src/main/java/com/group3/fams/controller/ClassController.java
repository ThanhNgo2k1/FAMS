package com.group3.fams.controller;

import com.group3.fams.DTO.request.CreateClassRequest;
import com.group3.fams.DTO.request.DeleteClassRequest;
import com.group3.fams.DTO.request.UpdateClassRequest;
import com.group3.fams.DTO.response.ResponseObject;
import com.group3.fams.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class ClassController {

  private final ClassService classService;

  @GetMapping(value = "/class/loadAll")
  @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
  public ResponseEntity<ResponseObject> loadAllClass() {
    return classService.getAllClass();
  }

  @PostMapping("/class/createClass")
  @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
  public ResponseEntity<ResponseObject> createClass(
      @RequestBody CreateClassRequest createClassRequest, BindingResult bindingResult) {
    return classService.createClass(createClassRequest, bindingResult);
  }

  @PostMapping(value = "/class/updateClass/{id}")
  @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
  public ResponseEntity<ResponseObject> updateClass(
      @RequestBody UpdateClassRequest updateClassRequest,
      @PathVariable int id,
      BindingResult bindingResult) {
    return classService.updateClass(updateClassRequest, id, bindingResult);
  }

  @PostMapping(value = "/class/deleteClass/{id}")
  @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
  public ResponseEntity<ResponseObject> deleteClass(
      DeleteClassRequest deleteClassRequest, @PathVariable int id, BindingResult bindingResult) {
    return classService.deleteClass(deleteClassRequest, id, bindingResult);
  }
}
