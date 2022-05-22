package dataBase.repository.test;

import dataBase.entity.test.Defect;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefectRepository extends JpaRepository<Defect, Long> {
    Defect findAllById(Long id);
}
