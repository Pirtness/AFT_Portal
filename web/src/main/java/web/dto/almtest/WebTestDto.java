package web.dto.almtest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import web.dto.BaseDto;

@EqualsAndHashCode(callSuper = true)
@Data
public class WebTestDto extends BaseDto {
    Long id;
    String name;
    Long parentId;
    String level;
    String zni;
    String status;
    String author;
    String visibleOrder;
    String lastModified;
    String creationTime;
    String isAutomatised;
    String regressOrder;
    String authorAutotest;
    String typeOp;
    String lastChange;
    String defects;
}
