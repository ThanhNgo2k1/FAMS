package com.group3.fams.init;

import com.group3.fams.entity.UserPermission;
import com.group3.fams.repositories.UserPermissionRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.group3.fams.Enum.PermissionManagement.FULL_ACCESS;
import static com.group3.fams.Enum.Role.*;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class DataLoader {

  @Autowired UserPermissionRepository userPermissionRepository;

  @PostConstruct
  public void loadInitialData() {
    if (userPermissionRepository.count() == 0) {
      UserPermission superAdminPermission =
          UserPermission.builder()
              .role(SUPER_ADMIN)
              .syllabusManagement(FULL_ACCESS)
              .trainingProgramManagement(FULL_ACCESS)
              .classManagement(FULL_ACCESS)
              .learningMaterialManagement(FULL_ACCESS)
              .userManagement(FULL_ACCESS)
              .build();
      userPermissionRepository.save(superAdminPermission);
      UserPermission adminPermission =
          UserPermission.builder()
              .role(ADMIN)
              .syllabusManagement(FULL_ACCESS)
              .trainingProgramManagement(FULL_ACCESS)
              .classManagement(FULL_ACCESS)
              .learningMaterialManagement(FULL_ACCESS)
              .userManagement(FULL_ACCESS)
              .build();
      userPermissionRepository.save(adminPermission);

      UserPermission trainerPermission =
          UserPermission.builder()
              .role(TRAINER)
              .syllabusManagement(FULL_ACCESS)
              .trainingProgramManagement(FULL_ACCESS)
              .classManagement(FULL_ACCESS)
              .learningMaterialManagement(FULL_ACCESS)
              .userManagement(FULL_ACCESS)
              .build();
      userPermissionRepository.save(trainerPermission);

      UserPermission traineePermission =
          UserPermission.builder()
              .role(TRAINEE)
              .syllabusManagement(FULL_ACCESS)
              .trainingProgramManagement(FULL_ACCESS)
              .classManagement(FULL_ACCESS)
              .learningMaterialManagement(FULL_ACCESS)
              .userManagement(FULL_ACCESS)
              .build();
      userPermissionRepository.save(traineePermission);
    }
  }
}
