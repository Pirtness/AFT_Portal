package core.test;

import dataBase.entity.test.*;
import dataBase.repository.test.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WebReportServiceImpl implements WebReportService {

    private final WebReportFolderRepository webReportFolderRepository;
    private final WebReportDefectRepository webReportDefectRepository;
    private final WebReportTestLastRunRepository webReportTestLastRunRepository;
    private final WebTestRepository webTestRepository;
    private final ImplTestRepository implTestRepository;
    private final TestRunRepository testRunRepository;
    private final RunsAndAutomationReviewRepository runsAndAutomationReviewRepository;

    @Override
    public List<WebReportFolder> getAllFolders() {
        //return webReportFolderRepository.findAllByLevelGreaterThanAndIdNotInOrderByLevelDesc(4L, Arrays.asList(0L, 2L));
        return webReportFolderRepository.findAll(Sort.by(Sort.Direction.DESC, "level"));
    }

    @Override
    public List<WebReportFolder> getChildrenByDept(Long parentFolderId, Integer dept) {
        List<WebReportFolder> webReportFolders = getChildren(parentFolderId);
        if (dept > 1) {
            List<WebReportFolder> webReportFoldersCopy = new ArrayList<>(webReportFolders);
            for (WebReportFolder webReportFolder : webReportFoldersCopy) {
                webReportFolders.addAll(getChildrenByDept(webReportFolder.getId(), dept - 1));
            }
        }
        return webReportFolders;
    }

    @Override
    public List<WebReportFolder> getChildren(Long parentFolderId) {
        return webReportFolderRepository.findAllByParentIdEqualsOrderByName(parentFolderId);
    }

    @Override
    public WebReportFolder getRootFolder() {
        List<WebReportFolder> webReportFolders = webReportFolderRepository.findAllByParentIdIsNull();
        if (webReportFolders.size() > 1) {
            throw new RuntimeException("find more than one root folder");
        }

        if (webReportFolders.size() == 0) {
            throw new RuntimeException("not found root folder");
        }

        return webReportFolders.get(0);
    }

    @Override
    public List<WebReportDefect> getReportDefect() {
        return webReportDefectRepository.findAll();
    }

    @Override
    public WebReportDefect getReportDefectTotal() {
        List<WebReportDefect> reportDefect = getReportDefect();

        WebReportDefect webReportDefectTotal = new WebReportDefect();
        webReportDefectTotal.setReleaseFound("total");
        webReportDefectTotal.setCntCritical(0);
        webReportDefectTotal.setCntHigh(0);
        webReportDefectTotal.setCntLow(0);
        webReportDefectTotal.setCntMedium(0);
        webReportDefectTotal.setCntVeryImportant(0);

        for (WebReportDefect defect : reportDefect) {
            webReportDefectTotal.setCntVeryImportant(defect.getCntVeryImportant() + webReportDefectTotal.getCntVeryImportant());
            webReportDefectTotal.setCntMedium(defect.getCntMedium() + webReportDefectTotal.getCntMedium());
            webReportDefectTotal.setCntLow(defect.getCntLow() + webReportDefectTotal.getCntLow());
            webReportDefectTotal.setCntHigh(defect.getCntHigh() + webReportDefectTotal.getCntHigh());
            webReportDefectTotal.setCntCritical(defect.getCntCritical() + webReportDefectTotal.getCntCritical());
        }

        return webReportDefectTotal;
    }

    @Override
    public List<WebReportTestLastRun> getChildrenTestLastRuns(Long parentFolderId) {
        List<WebReportTestLastRun> testLastRuns = webReportTestLastRunRepository.findAllByParentIdEqualsOrderById(parentFolderId);
        return testLastRuns;
    }

    @Override
    public List<WebReportTestLastRunRepository.TestInfo> getAllTestLastRuns() {
        return webReportTestLastRunRepository.getTestInfo();
    }

    @Override
    public List<WebTest> getChildrenNotImplTest(Long parentFolderId) {
        return webTestRepository.findAllNotImplByParentId(parentFolderId);
    }

    @Override
    public List<WebTest> getChildrenImplButNotReportedTest(Long parentFolderId) {
        return webTestRepository.findAllImplButNotReportedByParentId(parentFolderId);
    }

    @Override
    public WebReportTestLastRun getTestRun(Long testId, Integer isIdent) {
        List<WebReportTestLastRun> testRun = webReportTestLastRunRepository.findAllById(testId);
        if (testRun.size() > 1) {
            throw new RuntimeException("find more than one test");
        }

        if (testRun.size() == 0) {
            return null;
        }

        return testRun.get(0);
    }

    @Override
    public WebTest getWebTest(Long testId) {
        List<WebTest> webTests = webTestRepository.findAllById(testId);
        if (webTests.size() > 1) {
            throw new RuntimeException("find more than one test");
        }

        if (webTests.size() == 0) {
            throw new RuntimeException("not found test");
        }

        return webTests.get(0);
    }

    @Override
    public ImplTest getImplTest(Long testId) {
        List<ImplTest> implTests = implTestRepository.findAllById(testId);
        if (implTests.size() > 1) {
            throw new RuntimeException("find more than one test");
        }

        if (implTests.size() == 0) {
            return null;
        }

        return implTests.get(0);
    }

    @Override
    public List<String> getErrorMessage(Long testId, Integer isIdent) {
        return webReportTestLastRunRepository.getErrorByTestId(testId);
    }

    @Override
    public Date getStartDateOfRun(Long testId, Integer isIdent) {
        return webReportTestLastRunRepository.getStartDateOfRunByTestId(testId);
    }

    @Override
    public String getLastBuildName() {
        return testRunRepository.getLastBuildName();
    }

    @Override
    public List<String> getRemoteLogs(Long testId, Integer isIdent) {
        byte[] byteLogs = webReportTestLastRunRepository.getRemoteLogs(testId);
        if (byteLogs == null)
            return null;

        List<String> logs = new LinkedList<>(Arrays.asList(new String(byteLogs).split("\n")));

        int i = 1;
        while (i < logs.size()) {
            if (logs.get(i).startsWith("[")) {
                i += 1;
                continue;
            }
            logs.set(i-1, logs.get(i-1).concat(logs.get(i)));
            logs.remove(i);
        }
        return logs;
    }

    @Override
    public RunsAndAutomationReview getRunsAndAutomationReviewByTestGroupId(Long idTestGroup) {
        List<RunsAndAutomationReview> runsAndAutomationReview = runsAndAutomationReviewRepository.findAllByIdTestGroup(idTestGroup);
        if (runsAndAutomationReview.size() > 1) {
            throw new RuntimeException("find more than one row");
        }

        if (runsAndAutomationReview.size() == 0) {
            return null;
        }

        return runsAndAutomationReview.get(0);
    }

}
