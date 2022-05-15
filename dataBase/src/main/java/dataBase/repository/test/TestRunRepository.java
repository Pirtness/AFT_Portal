package dataBase.repository.test;

import dataBase.entity.test.TestRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TestRunRepository extends JpaRepository<TestRun, Long> {
    @Query(value = "select distinct tr.buildName from TestRun tr" +
            " where tr.startDate = (" +
            " select max(tr2.startDate) from TestRun tr2)")
    String getLastBuildName();
}
