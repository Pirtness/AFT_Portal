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
@Table(name = "test_run_step_embed")
public class TestRunStepEmbed {
    @Id
    private Long id;

    @Column(name = "id_test_run_step")
    private Long idTestRunStep;

    @Column(name = "embed")
    private byte[] embed;

    @Column(name = "mime_type")
    private String mimeType;
}
