package web.dto.almtest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import web.dto.BaseDto;

@EqualsAndHashCode(callSuper = true)
@Data
public class WebReportDefectDto extends BaseDto {
    String releaseFound;
    Integer cntCritical;
    Integer cntVeryImportant;
    Integer cntHigh;
    Integer cntMedium;
    Integer cntLow;
}
