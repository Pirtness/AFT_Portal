package dataBase.entity.test;

import com.fasterxml.jackson.annotation.JsonRawValue;
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
@Table(name = "test_run_step")
public class TestRunStep {
    @Id
    private Long id;

    @Column(name = "id_test_run_package")
    private Long idTestRunPackage;

    @Column(name = "caption")
    private String caption;

    @Column(name = "status")
    private String status;

    @Column(name = "run_order")
    private Integer runOrder;

    @Column(name = "date_start")
    private Date dateStart;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "error_exists")
    private Boolean errorExists;

    @Column(name = "error")
    private String error;

    @Column(name = "doc")
    private String doc;

    @Column(name = "step_table", columnDefinition = "json")
    @JsonRawValue
    private String stepTable;

    @Column(name = "id_child_test_run_package")
    private Long idChildTestRunPackage;

}
