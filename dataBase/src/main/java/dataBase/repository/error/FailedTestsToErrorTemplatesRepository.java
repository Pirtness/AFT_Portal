package dataBase.repository.error;

import dataBase.entity.error.FailedTestsToErrorTemplates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FailedTestsToErrorTemplatesRepository extends JpaRepository<FailedTestsToErrorTemplates, Long> {
    List<FailedTestsToErrorTemplates> findAll();

    List<FailedTestsToErrorTemplates> findAllByIdTemplate(Long idTemplate);

}
