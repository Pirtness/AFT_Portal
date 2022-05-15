package web.dto.almtest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import web.dto.BaseDto;

@EqualsAndHashCode(callSuper = true)
@Data
public class RunsAndAutomationReviewDto extends BaseDto {
    Long idTestGroup;
    Long totalAlmTestsAmount;
    Long automatedTestsAmount;
    Long newTestsInPrAmount;
    Long failedTestsAmount;
    Long passedTestsAmount;
    String buildName;
    String testRunStartDate;
    String reportCreationDate;
    String description;
}
