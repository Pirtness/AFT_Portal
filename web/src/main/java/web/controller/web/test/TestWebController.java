package web.controller.web.test;

import core.test.WebReportService;
import core.test.enums.TestGroupsEnum;
import dataBase.entity.test.RunsAndAutomationReview;
import dataBase.entity.test.WebReportDefect;
import dataBase.entity.test.WebReportFolder;
import dataBase.entity.test.WebTest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import web.converter.RunsAndAutomationReviewDtoConverter;
import web.converter.WebReportDefectDtoConverter;
import web.converter.WebReportFolderDtoConverter;
import web.converter.WebTestDtoConverter;

@Controller
@RequiredArgsConstructor
public class TestWebController {

    private final WebReportService webReportService;
    private final WebReportFolderDtoConverter webReportFolderDtoConverter;
    private final WebReportDefectDtoConverter webReportDefectDtoConverter;
    private final WebTestDtoConverter webTestDtoConverter;
    private final RunsAndAutomationReviewDtoConverter runsAndAutomationReviewDtoConverter;

    @GetMapping({"/testFolders","/testFolders.html","/test_folders","/test_folders.html"})
    public String testFolderPage(Model model) {
        final WebReportFolder webReportFolder = webReportService.getRootFolder();
        model.addAttribute("rootFolder", webReportFolderDtoConverter.createFrom(webReportFolder));
        model.addAttribute("buildName", webReportService.getLastBuildName());
        return "test/test_folders";
    }

    @GetMapping({"/defects.html","defects"})
    public String defectFolderPage(Model model) {
        final WebReportFolder webReportFolder = webReportService.getRootFolder();
        final WebReportDefect webReportDefectTotal = webReportService.getReportDefectTotal();
        model.addAttribute("rootFolder", webReportFolderDtoConverter.createFrom(webReportFolder));
        model.addAttribute("webReportDefectTotal", webReportDefectDtoConverter.createFrom(webReportDefectTotal));
        return "test/test_defects";
    }

    @GetMapping({"/tests.html","/tests"})
    public String testsPage(Model model) {
        return "test/tests";
    }

    @GetMapping({"/testInfo.html/{id}","/testInfo/{id}", "/test_info.html/{id}","/test_info/{id}"})
    public String testInfoPage(Model model, @PathVariable(value = "id") Long testId) {
        final WebTest webTest = webReportService.getWebTest(testId);
        model.addAttribute("webTest", webTestDtoConverter.createFrom(webTest));
        return "test/test_info.html";
    }

    @GetMapping({"/test_errors.html","/errors.html", "/test_errors", "/errors"})
    public String testErrors(Model model) {
        model.addAttribute("buildName", webReportService.getLastBuildName());
        return "test_errors/test_errors";
    }

    @GetMapping({"/main.html","/main"})
    public String mainPage(Model model) {
        RunsAndAutomationReview apiTests = webReportService.getRunsAndAutomationReviewByTestGroupId(TestGroupsEnum.API.getId());
        RunsAndAutomationReview uiTests = webReportService.getRunsAndAutomationReviewByTestGroupId(TestGroupsEnum.UI.getId());
        RunsAndAutomationReview qualityGate = webReportService.getRunsAndAutomationReviewByTestGroupId(TestGroupsEnum.QUALITY_GATE.getId());

        model.addAttribute("apiTests", runsAndAutomationReviewDtoConverter.createFrom(apiTests));
        model.addAttribute("uiTests", runsAndAutomationReviewDtoConverter.createFrom(uiTests));
        model.addAttribute("qualityGate", runsAndAutomationReviewDtoConverter.createFrom(qualityGate));
        return "main/main.html";
    }

}
