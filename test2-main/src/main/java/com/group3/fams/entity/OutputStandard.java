package com.group3.fams.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_outputStandard")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OutputStandard {

  @Id
  @Column(name = "output_standard_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer outputStandardId;

  @Column(name = "output_standard_name")
  String outputStandardName;

  @OneToMany(mappedBy = "outputStandards")
  @JsonManagedReference
  List<TrainingContent> trainingContents;
}
