package web.controller.api.almtests;

import core.test.WebReportService;
import dataBase.entity.test.WebReportDefect;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import web.converter.WebReportDefectDtoConverter;
import web.dto.almtest.WebReportDefectDto;

import java.util.List;

@RestController
@RequestMapping("/api/test/defectReport")
@RequiredArgsConstructor
public class WebReportDefectRestController {

    private final WebReportService webReportService;
    private final WebReportDefectDtoConverter webReportDefectDtoConverter;

    @GetMapping({"/", "/list"})
    @ResponseStatus(HttpStatus.OK)
    public List<WebReportDefectDto> getReport() {
        List<WebReportDefect> reportDefectList = webReportService.getReportDefect();
        return webReportDefectDtoConverter.createFromEntities(reportDefectList);
    }

}
