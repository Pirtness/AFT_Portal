package web.converter;

import dataBase.entity.test.RunsAndAutomationReview;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import web.dto.almtest.RunsAndAutomationReviewDto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component
@RequiredArgsConstructor
public class RunsAndAutomationReviewDtoConverterImpl implements RunsAndAutomationReviewDtoConverter {

    private final ModelMapper modelMapper;

    @Override
    public RunsAndAutomationReview createFrom(RunsAndAutomationReviewDto dto) {
        return updateEntity(new RunsAndAutomationReview(), dto);
    }

    @Override
    public RunsAndAutomationReviewDto createFrom(RunsAndAutomationReview entity) {
        RunsAndAutomationReviewDto dto = modelMapper.map(entity, RunsAndAutomationReviewDto.class);
        if (entity.getTestRunStartDate() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(entity.getTestRunStartDate());
            calendar.add(Calendar.HOUR_OF_DAY, 3);
            dto.setTestRunStartDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
        }
        return dto;
    }

    @Override
    public RunsAndAutomationReview updateEntity(RunsAndAutomationReview entity, RunsAndAutomationReviewDto dto) {
        throw new RuntimeException("Entity RunsAndAutomationReview does not must be updated or inserted");
    }
}
