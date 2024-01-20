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
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_trainingContent")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainingContent {

    @Id
    @Column(name = "unit_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer unitCode;

    @Column(name = "content")
    String content;

    @Column(name = "learning_objective")
    String learningObjective;

    @Column(name = "delivery_type")
    String deliveryType;

    @Column(name = "duration")
    Integer duration;

    @Column(name = "training_format")
    String trainingFormat;

    @Column(name = "note")
    String note;

    @OneToMany(mappedBy = "trainingContent")
    @JsonManagedReference
    List<TrainingUnit> trainingUnits;

    @ManyToOne
    @JoinColumn(name = "output_code")
    @JsonManagedReference
    OutputStandard outputStandards;


}
