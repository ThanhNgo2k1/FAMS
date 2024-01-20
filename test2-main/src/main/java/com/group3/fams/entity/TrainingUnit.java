package com.group3.fams.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_trainingUnit")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainingUnit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "training_unit_id")
  Integer trainingUnitId;

  @Column(name = "unit_name")
  String unitName;

  @Column(name = "day_number")
  String dayNumber;

  public TrainingUnit( String unitName, int dayNumber, Syllabus syllabus,
      TrainingContent trainingContent) {

    this.unitName = unitName;
    this.dayNumber = "Day " + dayNumber;
    this.syllabus = syllabus;
    this.trainingContent = trainingContent;
  }

  @ManyToOne
  @JoinColumn(name = "topic_code", referencedColumnName = "topic_code")
  @JsonBackReference
  Syllabus syllabus;

  @ManyToOne
  @JoinColumn(name = "unit_code", referencedColumnName = "unit_code")
  @JsonBackReference
  TrainingContent trainingContent;
}
