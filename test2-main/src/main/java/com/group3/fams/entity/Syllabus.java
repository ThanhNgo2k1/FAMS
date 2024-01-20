package com.group3.fams.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_syllabus")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Syllabus {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "topic_code")
  Integer topicCode;

  @Column(name = "topic_name")
  String topicName;

  @Column(name = "technical_group")
  String technicalGroup;

  @Column(name = "version")
  String version;

  @Column(name = "training_audience")
  String trainingAudience;

  @Column(name = "topic_outline")
  String topicOutline;

  @Column(name = "training_materials")
  String trainingMaterials;

  @Column(name = "training_principles")
  String trainingPrinciples;

  @Column(name = "priority")
  Integer priority;

  @Column(name = "publish_status")
  String publishStatus;

  @Column(name = "created_by")
  @CreatedBy
  String createdBy;

  @Column(name = "created_date")
  @CreatedDate
  LocalDateTime createdDate;

  @Column(name = "modified_by")
  @LastModifiedBy
  String modifiedBy;

  @Column(name = "modified_date")
  @LastModifiedDate
  LocalDateTime modifiedDate;

  @OneToMany(mappedBy = "syllabus")
  @JsonManagedReference(value = "trainingUnits")
  List<TrainingUnit> trainingUnits;

  //  @ManyToMany(mappedBy = "syllabusSet", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  //  Set<TrainingProgram> trainingProgramSet = new HashSet<>();
  //
  //  @ManyToMany(mappedBy = "syllabusSet", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  //  Set<LearningObjective> learningObjectiveSet = new HashSet<>();

  //  @OneToMany(mappedBy = "syllabi")
  //  @JsonManagedReference(value = "trainingObjectiveSyllabus")
  //  List<LearningObjectiveSyllabus> learningObjectiveSyllabi;

  @OneToMany(mappedBy = "syllabi", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonManagedReference(value = "trainingProgramSyllabus")
  List<TrainingProgramSyllabus> trainingProgramSyllabi;

  public Syllabus(Syllabus original) {
    this.topicName = original.topicName;
    this.technicalGroup = original.technicalGroup;
    this.version = original.version;
    this.trainingAudience = original.trainingAudience;
    this.topicOutline = original.topicOutline;
    this.trainingMaterials = original.trainingMaterials;
    this.trainingPrinciples = original.trainingPrinciples;
    this.priority = original.priority;
    this.publishStatus = original.publishStatus;
    this.createdBy = original.createdBy;
    this.createdDate = original.createdDate;
    this.modifiedBy = original.modifiedBy;
    this.modifiedDate = original.modifiedDate;
    // Khởi tạo một ArrayList mới để tránh thay đổi list gốc khi list copy được thay đổi
    this.trainingUnits = new ArrayList<>(original.trainingUnits);
    this.trainingProgramSyllabi = new ArrayList<>(original.trainingProgramSyllabi);
  }
}
