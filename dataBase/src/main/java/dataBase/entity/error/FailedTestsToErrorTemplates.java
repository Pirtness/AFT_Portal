package dataBase.entity.error;

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
@Table(name = "report_failed_test_to_error_template")
public class FailedTestsToErrorTemplates {
    @Id
    private Long id;

    @Column(name = "id_test")
    private Long idTest;

    @Column(name = "error_text")
    private byte[] errorText;

    @Column(name = "id_template")
    private Long idTemplate;

    @Column(name = "creation_time")
    private Date creationTime;
}