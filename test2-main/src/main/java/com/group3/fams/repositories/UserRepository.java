package com.group3.fams.repositories;

import com.group3.fams.entity.User;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findUserByEmail(String email);

  @NonNull
  Page<User> findAll(Pageable pageable);
}
