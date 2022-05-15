package dataBase.entity.test;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Immutable
@Table(name = "vi_web_report_defect")
public class WebReportDefect {
    @Id
    @Column(name = "release_found")
    String releaseFound;

    @Column(name = "cnt_critical")
    Integer cntCritical;

    @Column(name = "cnt_very_important")
    Integer cntVeryImportant;

    @Column(name = "cnt_high")
    Integer cntHigh;

    @Column(name = "cnt_medium")
    Integer cntMedium;

    @Column(name = "cnt_low")
    Integer cntLow;
}
