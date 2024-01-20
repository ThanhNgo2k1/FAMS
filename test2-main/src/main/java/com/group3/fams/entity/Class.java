package com.group3.fams.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_class")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Class {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "class_id")
  Integer classId;

  @Column(name = "class_name")
  String className;

  @Column(name = "class_Code")
  String classCode;

  @Column(name = "duration")
  Integer duration;

  @Column(name = "status")
  String status;

  @Column(name = "location")
  String location;

  @Column(name = "fsu")
  String fsu;

  @Column(name = "start_date")
  LocalDate startDate;

  @Column(name = "end_date")
  LocalDate endDate;

  @Column(name = "created_by")
  @CreatedBy
  String createdBy;

  @Column(name = "created_date")
  @CreatedDate
  LocalDate createdDate;

  @Column(name = "modified_by")
  @LastModifiedBy
  String modifiedBy;

  @Column(name = "modified_date")
  @LastModifiedBy
  LocalDate modifiedDate;

  @OneToMany(mappedBy = "clazzes")
  @JsonManagedReference(value = "classUser")
  List<ClassUser> classUsers;

  @ManyToOne
  @JoinColumn(name = "training_program_code", referencedColumnName = "training_program_code")
  @JsonBackReference
  TrainingProgram trainingProgram;
}
