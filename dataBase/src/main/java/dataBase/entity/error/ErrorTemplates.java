package dataBase.entity.error;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Immutable
@Table(name = "error_template")
public class ErrorTemplates {
    @Id
    private Long id;

    @Column(name = "error_template")
    private byte[] errorTemplate;

    @Column(name = "hashed_error_template")
    private byte[] hashedErrorTemplate;

    @Column(name = "description")
    private String description;
}