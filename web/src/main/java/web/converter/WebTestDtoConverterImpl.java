package web.converter;

import dataBase.entity.test.WebTest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import web.dto.almtest.WebTestDto;

@Component
@RequiredArgsConstructor
public class WebTestDtoConverterImpl implements WebTestDtoConverter {
    private final ModelMapper modelMapper;

    @Override
    public WebTest createFrom(WebTestDto dto) {
        return updateEntity(new WebTest(), dto);
    }

    @Override
    public WebTestDto createFrom(WebTest entity) {
        WebTestDto dto = modelMapper.map(entity, WebTestDto.class);
        return dto;
    }

    @Override
    public WebTest updateEntity(WebTest entity, WebTestDto dto) {
        throw new RuntimeException("Entity WebTest does not must be updated or inserted");
    }
}
