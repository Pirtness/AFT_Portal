package web.converter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import web.dto.testerror.ErrorTemplateTestGroupDto;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@RequiredArgsConstructor
public class ErrorTemplateTestGroupDtoConverterImpl implements  ErrorTemplateTestGroupDtoConverter {
    private final ModelMapper modelMapper;

    @Override
    public Map<String, Object> createFrom(ErrorTemplateTestGroupDto dto) {
        throw new RuntimeException("Creating entity ErrorTemplateTestGroup from dto is not supported");
    }

    @Override
    public ErrorTemplateTestGroupDto createFrom(Map<String, Object> entity) {
        ErrorTemplateTestGroupDto dto = new ErrorTemplateTestGroupDto();
        dto.setIdTemplate(((BigInteger)entity.get("id_template")).longValue());
        dto.setDescription(entity.get("description") == null ? "" : entity.get("description").toString());
        dto.setErrorTemplate(entity.get("error_template") == null ? "" : new String((byte[]) entity.get("error_template"), StandardCharsets.UTF_8));
        List<String> testIds = Arrays.asList((entity.get("test_ids").toString()).split(", "));
        testIds.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Long.valueOf(o1).compareTo(Long.valueOf(o2));
            }
        });
        dto.setTestIds(testIds);
        return dto;
    }

    @Override
    public Map<String, Object> updateEntity(Map<String, Object> entity, ErrorTemplateTestGroupDto dto) {
        throw new RuntimeException("Entity ErrorTemplateTestGroup does not must be updated or inserted");
    }
}