package web.dto.almtest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import web.dto.BaseDto;

@EqualsAndHashCode(callSuper = true)
@Data
public class WebReportTestLastRunDto extends BaseDto {
    Long id;
    Long parentId;
    String name;
    String lastBuildName;
    String lastStatus;
    String prevStatus;
    Long idLastPassRun;
    String lastPassBuildName;
    Long cntRun;
    Long cntPass;
    Long cntFail;
    Integer isIdentApp;
    Long idLastRun;
}
