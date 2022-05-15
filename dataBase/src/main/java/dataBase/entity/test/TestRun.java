package dataBase.entity.test;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Immutable
@Table(name = "test_run")
public class TestRun {
    @Id
    private Long id;

    @Column(name = "build_name")
    private String buildName;

    @Column(name = "build_date")
    private Date buildDate;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "stand")
    private String stand;

    @Column(name = "description")
    private String description;

}
