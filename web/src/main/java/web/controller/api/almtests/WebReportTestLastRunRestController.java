package web.controller.api.almtests;

import core.test.WebReportService;
import core.test.enums.ErrorType;
import dataBase.entity.test.WebReportTestLastRun;
import dataBase.repository.test.WebReportTestLastRunRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;
import web.converter.WebReportTestLastRunDtoConverter;
import web.dto.almtest.WebReportTestLastRunDto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/test/testLastRunReport")
@RequiredArgsConstructor
public class WebReportTestLastRunRestController {
    private final WebReportService webReportService;
    private final WebReportTestLastRunDtoConverter webReportTestLastRunDtoConverter;

    @GetMapping("/getChildren")
    public Collection<WebReportTestLastRunDto> getChildrenTests(@RequestParam(name="parentFolderId") Long parentFolderId) {
        final Collection<WebReportTestLastRun> tests = webReportService.getChildrenTestLastRuns(parentFolderId);
        return webReportTestLastRunDtoConverter.createFromEntities(tests);
    }

    @GetMapping("")
    public Collection<WebReportTestLastRunRepository.TestInfo> getAll() {
        final Collection<WebReportTestLastRunRepository.TestInfo> tests = webReportService.getAllTestLastRuns();
        return tests;
    }

    @GetMapping("/{id}")
    public WebReportTestLastRunDto getTestLastRun(@PathVariable(name="id") Long testId,
                                                                @RequestParam(name="isIdent") Integer isIdent) {
        final WebReportTestLastRun testLastRun = webReportService.getTestRun(testId, isIdent);
        return testLastRun == null ? null : webReportTestLastRunDtoConverter.createFrom(testLastRun);
    }

    @GetMapping("/{id}/getError")
    public Pair<List<String>, ErrorType> getErrorMessage(@PathVariable(name="id") Long testId,
                                                         @RequestParam(name="isIdent") Integer isIdent) {
        List<String> error = webReportService.getErrorMessage(testId, isIdent);
        if (error.size() > 0)
            return Pair.of(error, ErrorType.getErrorType(error.get(0)));
        return null;
    }

    @GetMapping("/{id}/getStartDate")
    public String  getStartDate(@PathVariable(name="id") Long testId,
                              @RequestParam(name="isIdent") Integer isIdent) {
        Date date = webReportService.getStartDateOfRun(testId, isIdent);
        if (date == null)
            return "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 3);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(calendar.getTime());
    }

    @GetMapping("/{id}/getRemoteLogs")
    public List<String> getRemoteLogs(@PathVariable(name="id") Long testId,
                                @RequestParam(name="isIdent") Integer isIdent) {
        return webReportService.getRemoteLogs(testId, isIdent);
    }

    @GetMapping("/getNewFailed")
    public List<String> getNewFailedTests() {
        return webReportService.getNewFailedTests();
    }
}
