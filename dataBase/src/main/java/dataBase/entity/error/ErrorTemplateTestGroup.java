package dataBase.entity.error;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ErrorTemplateTestGroup {

    @Getter
    @Setter
    private Long idTemplate;

    @Getter
    @Setter
    private byte[] errorTemplate;

    @Getter
    @Setter
    private String testIds;
}