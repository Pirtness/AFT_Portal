package web.controller.api.testerror;

import core.test.WebReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import web.converter.ErrorTemplateTestGroupDtoConverter;
import web.dto.testerror.ErrorTemplateTestGroupDto;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api/test/errors")
@RequiredArgsConstructor
public class FailedTestGroupsController {
    private final WebReportService webReportService;
    private final ErrorTemplateTestGroupDtoConverter errorTemplateTestGroupDtoConverter;

    @GetMapping("/testGroups")
    public ArrayList<ErrorTemplateTestGroupDto> getTestGroups() {
        final ArrayList<Map<String, Object>> errorTemplateTestGroups = webReportService.getErrorTemplateTestGroups();
        ArrayList<ErrorTemplateTestGroupDto> errorTemplateTestGroupDtos = new ArrayList<>();
        for (Map<String, Object> errorTemplateTestGroup : errorTemplateTestGroups) {
            errorTemplateTestGroupDtos.add(errorTemplateTestGroupDtoConverter.createFrom(errorTemplateTestGroup));
        }
        return errorTemplateTestGroupDtos;
    }
}