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

    @GetMapping({"/main.html","/main"})
    public String mainPage(Model model) {
//        RunsAndAutomationReview allTests = webReportService.getRunsAndAutomationReviewByTestGroupId(TestGroupsEnum.ALL.getId());
//        RunsAndAutomationReview firstPriorityTests = webReportService.getRunsAndAutomationReviewByTestGroupId(TestGroupsEnum.REGRESS_FIRST_PRIORITY.getId());
//        RunsAndAutomationReview qualityGateTests = webReportService.getRunsAndAutomationReviewByTestGroupId(TestGroupsEnum.QUALITY_GATE.getId());
        RunsAndAutomationReview sowaTests = webReportService.getRunsAndAutomationReviewByTestGroupId(TestGroupsEnum.SOWA.getId());
        RunsAndAutomationReview sowaTests2 = webReportService.getRunsAndAutomationReviewByTestGroupId(5L);

//        model.addAttribute("allTests", runsAndAutomationReviewDtoConverter.createFrom(allTests));
//        model.addAttribute("firstPriorityTests", runsAndAutomationReviewDtoConverter.createFrom(firstPriorityTests));
//        model.addAttribute("qualityGateTests", runsAndAutomationReviewDtoConverter.createFrom(qualityGateTests));
        model.addAttribute("sowaTests", runsAndAutomationReviewDtoConverter.createFrom(sowaTests));
        model.addAttribute("sowaTests2", runsAndAutomationReviewDtoConverter.createFrom(sowaTests2));
        return "main/main.html";
    }

}
