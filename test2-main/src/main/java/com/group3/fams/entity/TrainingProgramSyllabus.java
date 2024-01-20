package com.group3.fams.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_training_program_syllabus")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class TrainingProgramSyllabus {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "training_program_syllasbus_id")
  Integer trainingProgramSyllabusId;

  @ManyToOne
  @JoinColumn(name = "training_program_code", referencedColumnName = "training_program_code")
  @JsonBackReference
  TrainingProgram trainingPrograms;

  @ManyToOne
  @JoinColumn(name = "syllabus_id", referencedColumnName = "topic_code")
  @JsonBackReference
  Syllabus syllabi;
}
