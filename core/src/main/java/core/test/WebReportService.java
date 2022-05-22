package core.test;

import dataBase.entity.test.*;
import dataBase.repository.test.WebReportTestLastRunRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface WebReportService {
    List<WebReportFolder> getAllFolders();

    List<WebReportFolder> getChildrenByDept(Long parentFolderId, Integer dept);

    List<WebReportFolder> getChildren(Long parentFolderId);

    WebReportFolder getRootFolder();

    List<WebReportDefect> getReportDefect();

    WebReportDefect getReportDefectTotal();

    List<WebReportTestLastRun> getChildrenTestLastRuns(Long parentFolderId);

    List<WebReportTestLastRunRepository.TestInfo> getAllTestLastRuns();

    List<WebTest> getChildrenNotImplTest(Long parentFolderId);

    List<WebTest> getChildrenImplButNotReportedTest(Long parentFolderId);

    WebReportTestLastRun getTestRun(Long testId, Integer isIdent);

    WebTest getWebTest(Long testId);

    ImplTest getImplTest(Long testId);

    List<String> getErrorMessage(Long testId, Integer isIdent);

    Date getStartDateOfRun(Long testId, Integer isIdent);

    String getLastBuildName();

    List<String> getRemoteLogs(Long testId, Integer isIdent);

    RunsAndAutomationReview getRunsAndAutomationReviewByTestGroupId(Long idTestGroup);

    ArrayList<Map<String, Object>> getErrorTemplateTestGroups();

    List<String> getNewFailedTests();

    Defect getDefect(Long id);
}
