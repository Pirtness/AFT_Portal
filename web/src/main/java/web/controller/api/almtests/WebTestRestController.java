package web.controller.api.almtests;

import core.test.WebReportService;
import dataBase.entity.test.WebTest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.converter.WebTestDtoConverter;
import web.dto.almtest.WebTestDto;

import java.util.Collection;

@RestController
@RequestMapping("/api/test/almTest")
@RequiredArgsConstructor
public class WebTestRestController {
    private final WebReportService webReportService;
    private final WebTestDtoConverter webTestDtoConverter;

    @GetMapping("/getChildren")
    public Collection<WebTestDto> getChildrenTests(@RequestParam(name = "parentFolderId") Long parentFolderId) {
        final Collection<WebTest> tests = webReportService.getChildrenNotImplTest(parentFolderId);
        return webTestDtoConverter.createFromEntities(tests);
    }

    @GetMapping("ImplNorReported/getChildren")
    public Collection<WebTestDto> getChildrenImplNotReportedTests(@RequestParam(name = "parentFolderId") Long parentFolderId) {
        final Collection<WebTest> tests = webReportService.getChildrenImplButNotReportedTest(parentFolderId);
        return webTestDtoConverter.createFromEntities(tests);
    }
}
