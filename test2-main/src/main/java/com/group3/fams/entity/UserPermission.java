package com.group3.fams.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.group3.fams.Enum.PermissionManagement;
import com.group3.fams.Enum.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_userPermission")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPermission {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "permission_id")
  Integer permissionId;

  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  Role role;

  @Enumerated(EnumType.STRING)
  @Column(name = "syllabus_management")
  PermissionManagement syllabusManagement;

  @Enumerated(EnumType.STRING)
  @Column(name = "training_program_management")
  PermissionManagement trainingProgramManagement;

  @Enumerated(EnumType.STRING)
  @Column(name = "class_management")
  PermissionManagement classManagement;

  @Enumerated(EnumType.STRING)
  @Column(name = "learning_material_management")
  PermissionManagement learningMaterialManagement;

  @Enumerated(EnumType.STRING)
  @Column(name = "user_management")
  PermissionManagement userManagement;

  @OneToMany(mappedBy = "userPermission")
  @JsonManagedReference(value = "user")
  List<User> users;
}
