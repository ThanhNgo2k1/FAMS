package com.group3.fams.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  Integer userId;

  @Column(name = "name")
  String name;

  @Column(name = "email")
  String email;

  @Column(name = "password")
  String password;

  @Column(name = "phone")
  String phone;

  @Column(name = "dob")
  LocalDate dob;

  @Column(name = "gender")
  String gender;

  @Column(name = "status")
  String status;

  @Column(name = "created_by")
  String createdBy;

  @Column(name = "created_date")
  LocalDate createdDate;

  @Column(name = "modified_by")
  String modifiedBy;

  @Column(name = "modified_date")
  LocalDate modifiedDate;

  @ManyToOne
  @JoinColumn(name = "permission_id", referencedColumnName = "permission_id")
  @JsonBackReference
  UserPermission userPermission;

  @OneToMany(mappedBy = "users")
  @JsonManagedReference
  List<ClassUser> classUser;

  @OneToMany(mappedBy = "user")
  @JsonManagedReference(value = "trainingPrograms")
  List<TrainingProgram> trainingPrograms;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(userPermission.getRole().toString()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
