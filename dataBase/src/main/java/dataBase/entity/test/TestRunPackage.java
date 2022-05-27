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
@Table(name = "test_run_package")
public class TestRunPackage {
    @Id
    private Long id;

    @Column(name = "id_test_run")
    private Long idTestRun;

    @Column(name = "id_alm_test")
    private Long idAlmTest;

    @Column(name = "id_parent_package")
    private Long idParentPackage;

    @Column(name = "run_order")
    private Integer runOrder;

    @Column(name = "caption")
    private String caption;

    @Column(name = "status")
    private String status;

    @Column(name = "date_start")
    private Date dateStart;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "error_exists")
    private Boolean errorExists;

    @Column(name = "type")
    private String type;

    @Column(name = "is_root")
    private Boolean isRoot;

    @Column(name = "description")
    private String description;

}
