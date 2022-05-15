package web.converter;

import dataBase.entity.test.WebReportTestLastRun;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import web.dto.almtest.WebReportTestLastRunDto;

@Component
@RequiredArgsConstructor
public class WebReportTestLastRunDtoConverterImpl implements  WebReportTestLastRunDtoConverter{
    private final ModelMapper modelMapper;

    @Override
    public WebReportTestLastRun createFrom(WebReportTestLastRunDto dto) {
        return updateEntity(new WebReportTestLastRun(), dto);
    }

    @Override
    public WebReportTestLastRunDto createFrom(WebReportTestLastRun entity) {
        WebReportTestLastRunDto dto = modelMapper.map(entity, WebReportTestLastRunDto.class);
        return dto;
    }

    @Override
    public WebReportTestLastRun updateEntity(WebReportTestLastRun entity, WebReportTestLastRunDto dto) {
        throw new RuntimeException("Entity WebReportTestLastRun does not must be updated or inserted");
    }
}
