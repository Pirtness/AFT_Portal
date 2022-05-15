package web.controller.api.almtests;

import core.test.WebReportService;
import dataBase.entity.test.WebReportFolder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import web.converter.WebReportFolderDtoConverter;
import web.dto.almtest.WebReportFolderDto;

import java.util.Collection;

@RestController
@RequestMapping("/api/test/folderReport")
@RequiredArgsConstructor
public class WebReportFolderRestController {

    private final WebReportService webReportService;
    private final WebReportFolderDtoConverter webReportFolderDtoConverter;

    @GetMapping({"/", "/list"})
    @ResponseStatus(HttpStatus.OK)
    public Collection<WebReportFolderDto> getList() {
        final Collection<WebReportFolder> testFolderReports = webReportService.getAllFolders();
        return webReportFolderDtoConverter.createFromEntities(testFolderReports);
    }

    @GetMapping("/getChildren")
    public Collection<WebReportFolderDto> getChildrenFolders(@RequestParam(name="parentFolderId") Long parentFolderId) {
        final Collection<WebReportFolder> testFolders = webReportService.getChildrenByDept(parentFolderId,1);
        return webReportFolderDtoConverter.createFromEntities(testFolders);
    }

    @GetMapping("/getBuildName")
    public String getLastBuildName() {
        return webReportService.getLastBuildName();
    }

}
