package com.group3.fams.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_trainingProgram")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainingProgram {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "training_program_code")
  Integer trainingProgramCode;

  @Column(name = "name")
  String name;

  @Column(name = "start_time")
  @CreatedDate
  LocalDateTime startTime;

  @Column(name = "duration")
  Integer duration;

  @Column(name = "topic_code")
  Integer topicCode;

  @Column(name = "status")
  String status;

  @Column(name = "created_by")
  String createdBy;

  @Column(name = "created_date")
  LocalDateTime createdDate;

  @Column(name = "modified_by")
  String modifiedBy;

  @Column(name = "modified_date")
  LocalDateTime modifiedDate;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  @JsonBackReference
  User user;

  @OneToMany(mappedBy = "trainingProgram")
  @JsonManagedReference(value = "trainingPrograms")
  List<Class> classes;

  //  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  //  @JoinTable(
  //      name = "tbl_training_program_syllabus",
  //      joinColumns = {@JoinColumn(name = "training_program_code")},
  //      inverseJoinColumns = {@JoinColumn(name = "topic_code")})
  //  Set<Syllabus> syllabusSet = new HashSet<>();

  @OneToMany(mappedBy = "trainingPrograms", cascade = CascadeType.REMOVE)
  @JsonManagedReference(value = "trainingProgramSyllabus")
  List<TrainingProgramSyllabus> trainingProgramSyllabi;
}
