package dataBase.repository.test;

import dataBase.entity.test.RunsAndAutomationReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RunsAndAutomationReviewRepository extends JpaRepository<RunsAndAutomationReview, Long> {
    List<RunsAndAutomationReview> findAllByIdTestGroup(Long testGroupId);
}
