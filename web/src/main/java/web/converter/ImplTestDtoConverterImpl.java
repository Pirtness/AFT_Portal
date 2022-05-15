package web.converter;

import dataBase.entity.test.ImplTest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import web.dto.almtest.ImplTestDto;

@Component
@RequiredArgsConstructor
public class ImplTestDtoConverterImpl implements ImplTestDtoConverter {
    private final ModelMapper modelMapper;

    @Override
    public ImplTest createFrom(ImplTestDto dto) {
        return updateEntity(new ImplTest(), dto);
    }

    @Override
    public ImplTestDto createFrom(ImplTest entity) {
        ImplTestDto dto = modelMapper.map(entity, ImplTestDto.class);
        return dto;
    }

    @Override
    public ImplTest updateEntity(ImplTest entity, ImplTestDto dto) {
        throw new RuntimeException("Entity ImplTest does not must be updated or inserted");
    }
}
