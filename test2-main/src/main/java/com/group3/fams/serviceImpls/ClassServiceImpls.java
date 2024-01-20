package com.group3.fams.serviceImpls;

import com.group3.fams.DTO.request.CreateClassRequest;
import com.group3.fams.DTO.request.DeleteClassRequest;
import com.group3.fams.DTO.request.UpdateClassRequest;
import com.group3.fams.DTO.response.ResponseObject;
import com.group3.fams.Mapper.ClassMapper;
import com.group3.fams.entity.Class;
import com.group3.fams.Enum.Role;
import com.group3.fams.entity.User;
import com.group3.fams.repositories.ClassRepository;
import com.group3.fams.repositories.UserRepository;
import com.group3.fams.service.ClassService;
import java.time.LocalDate;
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
public class ClassServiceImpls implements ClassService {

  private final ClassRepository classRepository;
  private final UserRepository userRepository;

  @Override
  public ResponseEntity<ResponseObject> getAllClass() {
    List<Class> classList = classRepository.findAll();
    if (classList.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(ResponseObject.builder().message("Empty").status("403").payload(null).build());
    }
    return ResponseEntity.status(HttpStatus.OK)
        .body(ResponseObject.builder().message("done").status("200").payload(classList).build());
  }

  //    @Override
  //    public ResponseEntity<ResponseObject> createClass(CreateClassRequest createClassRequest,
  // BindingResult bindingResult) {
  //        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  //        String userEmail = authentication.getName();
  //
  //        if (bindingResult.hasErrors()) {
  //            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject
  //                    .builder()
  //                    .message("Create failed")
  //                    .status("400")
  //                    .build());
  //        }
  //
  //        Class newClass = ClassMapper.INSTANCE.createClassRequestToClass(createClassRequest);
  //        newClass.setCreatedBy(userEmail);
  //        classRepository.save(newClass);
  //
  //
  //        return ResponseEntity.status(HttpStatus.OK).body(ResponseObject
  //                .builder()
  //                .message("Create Successful")
  //                .status("200")
  //                .payload(newClass)
  //                .build());
  //
  //    }

  @Override
  public ResponseEntity<ResponseObject> createClass(
      CreateClassRequest createClassRequest, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(ResponseObject.builder().message("create failed").status("403").build());
    }

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userEmail = authentication.getName();
    //    Class clazz = Class
    //        .builder()
    //        .className(createClassRequest.getClassName())
    //        .classCode(createClassRequest.getClassCode())
    //        .duration(createClassRequest.getDuration())
    //        .status(createClassRequest.getStatus())
    //        .location(createClassRequest.getLocation())
    //        .fsu(createClassRequest.getFsu())
    //        .startDate(createClassRequest.getStartDate())
    //        .endDate(createClassRequest.getEndDate())
    //        .createdDate(LocalDate.now())
    //        .modifiedDate(LocalDate.now())
    //        .createdBy(userEmail)
    //        .build();
    Class newClass = ClassMapper.INSTANCE.createClassRequestToClass(createClassRequest);
    newClass.setCreatedBy(userEmail);
    newClass.setCreatedDate(LocalDate.now());
    classRepository.save(newClass);

    return ResponseEntity.status(HttpStatus.OK)
        .body(
            ResponseObject.builder()
                .message("Create Successful")
                .status("200")
                .payload(newClass)
                .build());
  }

  @Override
  public ResponseEntity<ResponseObject> updateClass(
      UpdateClassRequest updateClassRequest, int id, BindingResult bindingResult) {
    Class existingClass = classRepository.findById(id).orElse(null);

    if (existingClass != null && !bindingResult.hasErrors()) {

      Class updatedClass =
          ClassMapper.INSTANCE.updateClassRequestToClass(updateClassRequest, existingClass);
      classRepository.save(updatedClass);

      return ResponseEntity.status(HttpStatus.OK)
          .body(
              ResponseObject.builder()
                  .message("Super_Admin Update Successful")
                  .status("200")
                  .payload(updatedClass)
                  .build());
    }

    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(
            ResponseObject.builder()
                .message("Update failed")
                .status("403")
                .payload(existingClass)
                .build());
  }

  private boolean hasPermissionToUpdateUserPermission(String userEmail) {
    User user = userRepository.findUserByEmail(userEmail).orElse(null);
    return user != null && user.getUserPermission().getRole() == Role.SUPER_ADMIN;
  }

  @Override
  public ResponseEntity<ResponseObject> deleteClass(
      DeleteClassRequest deleteClassRequest, int id, BindingResult bindingResult) {
    Class existingClass = classRepository.findById(id).orElse(null);

    if (existingClass != null && !bindingResult.hasErrors()) {

      Class updatedClass = ClassMapper.INSTANCE.deleteClassRequestToClass(deleteClassRequest,
          existingClass);
      classRepository.save(updatedClass);

      return ResponseEntity.status(HttpStatus.OK).body(ResponseObject
          .builder()
          .message("De-active class Successful")
          .status("200")
          .payload(updatedClass)
          .build());
    }

    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseObject
        .builder()
        .message("De-active class Fail")
        .status("403")
        .payload(existingClass)
        .build());
  }
}
