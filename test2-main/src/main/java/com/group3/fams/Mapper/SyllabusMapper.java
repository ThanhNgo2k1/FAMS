package com.group3.fams.Mapper;

import com.group3.fams.DTO.request.CreateSyllabusRequest;
import com.group3.fams.DTO.request.UpdateSyllabusRequest;
import com.group3.fams.entity.Syllabus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface SyllabusMapper {

  SyllabusMapper INSTANCE = Mappers.getMapper(SyllabusMapper.class);

  @Mapping(source = "publishStatus", target = "publishStatus")
  @Mapping(source = "technicalGroup", target = "technicalGroup")
  @Mapping(source = "topicName", target = "topicName")
  @Mapping(source = "topicOutline", target = "topicOutline")
  @Mapping(source = "trainingAudience", target = "trainingAudience")
  @Mapping(source = "trainingMaterials", target = "trainingMaterials")
  @Mapping(source = "trainingPrinciples", target = "trainingPrinciples")
  //  @Mapping(source = "created_by", target = "createdBy")
  //  @Mapping(target = "createdDate", expression = "java(java.time.LocalDate.now())")
  Syllabus createSyllabusRequestToSyllabus(CreateSyllabusRequest createSyllabusRequest);

  @Mapping(source = "priority", target = "priority")
  @Mapping(source = "version", target = "version")
  @Mapping(source = "publishStatus", target = "publishStatus")
  @Mapping(source = "technicalGroup", target = "technicalGroup")
  @Mapping(source = "topicName", target = "topicName")
  @Mapping(source = "topicOutline", target = "topicOutline")
  @Mapping(source = "trainingAudience", target = "trainingAudience")
  @Mapping(source = "trainingMaterials", target = "trainingMaterials")
  @Mapping(source = "trainingPrinciples", target = "trainingPrinciples")
  Syllabus updateSyllabusRequestToSyllabus(
      UpdateSyllabusRequest updateSyllabusRequest, @MappingTarget Syllabus syllabus);
}
