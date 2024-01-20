package com.group3.fams.Mapper;

import com.group3.fams.DTO.request.UpdateUserRequest;
import com.group3.fams.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Mapping(source = "name", target = "name")
  @Mapping(source = "email", target = "email")
  @Mapping(source = "phone", target = "phone")
  @Mapping(source = "dob", target = "dob")
  @Mapping(source = "modifiedBy", target = "modifiedBy")
  @Mapping(target = "userPermission", ignore = true)
  User updateUserRequestToUser(UpdateUserRequest updateUserRequest, @MappingTarget User user);
}
