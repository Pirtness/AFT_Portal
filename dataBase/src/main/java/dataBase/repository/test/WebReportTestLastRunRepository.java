package dataBase.repository.test;

import dataBase.entity.test.WebReportTestLastRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface WebReportTestLastRunRepository extends JpaRepository<WebReportTestLastRun, Long> {
    List<WebReportTestLastRun> findAllByParentIdEqualsOrderById(Long parentId);

    List<WebReportTestLastRun> findAllById(Long id);

    @Query(value = "select trs.error from TestRunStep trs")
    List<String> getErrorByTestId(Long testId);

    @Query(value = "select tr.startDate from TestRun tr" +
            " where tr.id = (" +
            "  select tlr.idLastRun from WebReportTestLastRun tlr" +
            "  where tlr.id = :testId)")
    Date getStartDateOfRunByTestId(Long testId);

    @Query(value = "select e.embed from TestRunStepEmbed e" +
            " where e.idTestRunStep in (" +
            " select trs.id from TestRunStep trs" +
            " where trs.idTestRunPackage in (" +
            "   select trp.id from TestRunPackage trp" +
            "   where trp.idAlmTest = :testId" +
            "   and trp.idTestRun = (" +
            "     select tlr.idLastRun from WebReportTestLastRun tlr" +
            "     where tlr.id = :testId)" +
            "   and trp.caption = 'server log collector after scenario error'" +
            "   )" +
            " and trs.caption = 'server remote logs'" +
            " order by trs.id" +
            " )")
    byte[] getRemoteLogs(Long testId);

    //todo change to report_test_runs
    @Query(value =
            "with rtr2 as (" +
                    "select rtr.id as id, rtr.name as name, rtr.id as almId, " +
                    " rtr.prev_status as prev_status, rtr.last_status as last_status" +
                    " from portal.vi_report_test_runs rtr" +
                    " order by rtr.id" +
                    ")" +
                    "select it.id, rtr2.almId, rtr2.name, it.tags, rtr2.prev_status as prevStatus, rtr2.last_status as lastStatus " +
                    "from portal.impl_test it" +
                    " inner join rtr2" +
                    " on rtr2.id = it.id" +
                    " order by rtr2.almId, rtr2.id",
            nativeQuery = true)

    List<TestInfo> getTestInfo();

    interface TestInfo {
        Long getId();

        Long getAlmId();

        String getName();

        String getTags();

        String getLastStatus();

        String getPrevStatus();
    }

}
