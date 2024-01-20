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
@Table(name = "tbl_classUser")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClassUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "class_user_id")
  Integer classUserId;

  @Column(name = "user_type")
  String userType;

  @ManyToOne
  @JoinColumn(name = "class_id", referencedColumnName = "class_id")
  @JsonBackReference
  Class clazzes;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  @JsonBackReference
  User users;
}
