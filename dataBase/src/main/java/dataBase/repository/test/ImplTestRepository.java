package dataBase.repository.test;

import dataBase.entity.test.ImplTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImplTestRepository extends JpaRepository<ImplTest, Long> {
    List<ImplTest> findAllById(Long parentId);
}
