package com.group3.fams.repositories;

import com.group3.fams.entity.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidTokenRepository extends JpaRepository<InvalidToken, Integer> {

  boolean existsByToken(String token);
}
