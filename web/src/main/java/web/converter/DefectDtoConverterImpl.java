package web.converter;

import dataBase.entity.test.Defect;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import web.dto.almtest.DefectDto;

@Component
@RequiredArgsConstructor
public class DefectDtoConverterImpl implements DefectDtoConverter {
    private final ModelMapper modelMapper;

    @Override
    public Defect createFrom(DefectDto dto) {
        return updateEntity(new Defect(), dto);
    }

    @Override
    public DefectDto createFrom(Defect entity) {
        DefectDto dto = modelMapper.map(entity, DefectDto.class);
        return dto;
    }

    @Override
    public Defect updateEntity(Defect entity, DefectDto dto) {
        throw new RuntimeException("Entity Defect does not must be updated or inserted");
    }
}
