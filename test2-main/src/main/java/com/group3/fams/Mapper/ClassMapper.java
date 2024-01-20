package com.group3.fams.Mapper;

import com.group3.fams.DTO.request.CreateClassRequest;
import com.group3.fams.DTO.request.DeleteClassRequest;
import com.group3.fams.DTO.request.UpdateClassRequest;
import com.group3.fams.entity.Class;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ClassMapper {

  ClassMapper INSTANCE = Mappers.getMapper(ClassMapper.class);

  @Mapping(source = "className", target = "className")
  @Mapping(source = "classCode", target = "classCode")
  @Mapping(source = "duration", target = "duration")
  @Mapping(target = "status", constant = "active")
  @Mapping(source = "location", target = "location")
  @Mapping(source = "fsu", target = "fsu")
  @Mapping(source = "startDate", target = "startDate")
  @Mapping(source = "endDate", target = "endDate")
  Class createClassRequestToClass(CreateClassRequest createClassRequest);

  @Mapping(source = "className", target = "className")
  @Mapping(source = "classCode", target = "classCode")
  @Mapping(source = "duration", target = "duration")
  @Mapping(target = "status", constant = "active")
  @Mapping(source = "location", target = "location")
  @Mapping(source = "fsu", target = "fsu")
  @Mapping(source = "startDate", target = "startDate")
  @Mapping(source = "endDate", target = "endDate")
  Class updateClassRequestToClass(
      UpdateClassRequest updateClassRequest, @MappingTarget Class newClass);

  @Mapping(target = "status", constant = "de-active")
  Class deleteClassRequestToClass(
      DeleteClassRequest deleteClassRequest, @MappingTarget Class deletedClass);
}
