package web.converter;

import dataBase.entity.test.WebReportDefect;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import web.dto.almtest.WebReportDefectDto;

@Component
@RequiredArgsConstructor
public class WebReportDefectDtoConverterImpl implements WebReportDefectDtoConverter {

    private final ModelMapper modelMapper;

    @Override
    public WebReportDefect createFrom(WebReportDefectDto dto) {
        return updateEntity(new WebReportDefect(), dto);
    }

    @Override
    public WebReportDefectDto createFrom(WebReportDefect entity) {
        return modelMapper.map(entity, WebReportDefectDto.class);
    }

    @Override
    public WebReportDefect updateEntity(WebReportDefect entity, WebReportDefectDto dto) {
        modelMapper.map(dto, entity);
        return entity;
    }
}
