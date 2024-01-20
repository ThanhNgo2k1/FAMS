package com.group3.fams.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Data
@Table(name = "InvalidToken")
public class InvalidToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  String token;
}
