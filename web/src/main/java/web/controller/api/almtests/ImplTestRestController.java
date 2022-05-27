package web.controller.api.almtests;

import core.test.WebReportService;
import dataBase.entity.test.ImplTest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import web.converter.ImplTestDtoConverter;
import web.dto.almtest.ImplTestDto;

@RestController
@RequestMapping("/api/test/implTest")
@RequiredArgsConstructor
public class ImplTestRestController {
    private final WebReportService webReportService;
    private final ImplTestDtoConverter implTestDtoConverter;

    @GetMapping("/{id}")
    public ImplTestDto getImplTest(@PathVariable(value = "id") Long testId,
                                   @RequestParam(name="isIdent") Integer isIdent) {
        if (isIdent == 1)
            testId = Long.valueOf("999" + testId.toString());
        final ImplTest implTest = webReportService.getImplTest(testId);
        return implTest == null ? null : implTestDtoConverter.createFrom(implTest);
    }
}
