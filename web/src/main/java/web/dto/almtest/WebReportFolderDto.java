package web.dto.almtest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import web.dto.BaseDto;

@EqualsAndHashCode(callSuper = true)
@Data
public class WebReportFolderDto extends BaseDto {
    Long id;
    Long parentId;
    String name;
    String lastModified;
    String visibleOrder;
    String level;
    Integer cntAll;
    Integer cntForIdApp;
    Integer cntPass;
    Integer cntFail;
    Integer cntWarn;
    Integer cntImpl;
    Integer cntPr;
    Integer cntDefect;
    String typeOp;
    String lastChange;
    Integer daysFromChange;
}
