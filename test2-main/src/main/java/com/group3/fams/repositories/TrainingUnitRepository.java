package com.group3.fams.repositories;

import com.group3.fams.entity.TrainingContent;
import com.group3.fams.entity.TrainingUnit;
import com.group3.fams.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingUnitRepository extends JpaRepository<TrainingUnit, Integer> {
  List<TrainingUnit> findByDayNumber(String dayNumber);
}