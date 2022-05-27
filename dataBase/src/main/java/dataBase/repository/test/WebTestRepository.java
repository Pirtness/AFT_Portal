package dataBase.repository.test;

import dataBase.entity.test.WebTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WebTestRepository extends JpaRepository<WebTest, Long> {
    List<WebTest> findAllById(Long testId);

    @Query(value = "select wt from WebTest wt" +
            " where wt.parentId = :parentId" +
            " and not exists (" +
            "  select it.id from ImplTest it" +
            "  where it.id = wt.id)")
    List<WebTest> findAllNotImplByParentId(Long parentId);

    @Query(value = "select wt from WebTest wt" +
            " where wt.parentId = :parentId" +
            " and exists (" +
            "  select it.id from ImplTest it" +
            "  where it.id = wt.id)" +
            " and not exists (" +
            "  select tlr.id from WebReportTestLastRun tlr" +
            "  where tlr.id = wt.id)")
    List<WebTest> findAllImplButNotReportedByParentId(Long parentId);

    interface QualityGate {
        Long getFailedAmount();

        Long getWholeAmount();

        String getBuildName();

        String getStartDate();
    }
    interface AutomatedPercentage {
        Long getActualTestsAmount();

        Long getAutomatedTestsAmount();

        Long getIdAppAutomatedTestsAmount();

        Long getNewTestsInPrAmount();

        Long getPassedTestsAmount();

        Long getFailedTestsAmount();
    }
}
