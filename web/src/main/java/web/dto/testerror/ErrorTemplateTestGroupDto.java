package web.dto.testerror;

import lombok.Data;
import lombok.EqualsAndHashCode;
import web.dto.BaseDto;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorTemplateTestGroupDto extends BaseDto {
    Long idTemplate;
    String errorTemplate;
    List<String> testIds;
    String description;
}
