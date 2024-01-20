package com.group3.fams.repositories;

import com.group3.fams.entity.Syllabus;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SyllabusRepository extends JpaRepository<Syllabus, Integer> {

  Page<Syllabus> findAllByTopicNameContainingIgnoreCase(String topicName, Pageable pageable);

  Page<Syllabus> findAll(Pageable pageable);
}
