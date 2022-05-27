package web.dto.almtest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import web.dto.BaseDto;

@EqualsAndHashCode(callSuper = true)
@Data
public class ImplTestDto extends BaseDto {
    Long id;
    String tags;
    String relativePath;
}
