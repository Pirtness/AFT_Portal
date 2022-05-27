package web.converter;

import dataBase.entity.test.WebReportFolder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sbrf.esa.aft.healthcheck.utils.ConcurrentDateFormatAccess;
import web.dto.almtest.WebReportFolderDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
public class WebReportFolderDtoConverterImpl implements WebReportFolderDtoConverter {
    private final ModelMapper modelMapper;

    @Override
    public WebReportFolder createFrom(WebReportFolderDto dto) {
        return updateEntity(new WebReportFolder(), dto);
    }

    @Override
    public WebReportFolderDto createFrom(WebReportFolder entity) {
        WebReportFolderDto dto = modelMapper.map(entity, WebReportFolderDto.class);
//        int cntDaysFromChange = 0;
//        if (entity.getLastChange() != null) {
//            LocalDateTime lastChange = LocalDateTime.ofInstant(entity.getLastChange().toInstant(), ZoneId.systemDefault());
//            Period period = Period.between(lastChange.toLocalDate(), LocalDate.now());
//            cntDaysFromChange = period.getDays();
//
//            dto.setLastChange(new ConcurrentDateFormatAccess().convertToString(entity.getLastChange()));
//        }
//        dto.setDaysFromChange(cntDaysFromChange);

        return dto;
    }

    @Override
    public WebReportFolder updateEntity(WebReportFolder entity, WebReportFolderDto dto) {
        throw new RuntimeException("Entity WebReportFolder does not must be updated or inserted");
    }
}
