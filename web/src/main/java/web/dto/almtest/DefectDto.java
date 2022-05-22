package web.dto.almtest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import web.dto.BaseDto;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class DefectDto extends BaseDto {
    Long id;
    String name;
    String dateCreate;
    String lastModified;
    String typeDefect;
    String releaseFound;
    String releaseClosing;
    String foundState;
    String isBlocking;
    String status;
    String severity;
    String description;
}
