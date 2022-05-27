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
@Table(name = "defect")
public class Defect {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_create")
    private Date dateCreate;

    @Column(name = "last_modified")
    private Date lastModified;

    @Column(name = "type_defect")
    private String typeDefect;

    @Column(name = "release_found")
    private String releaseFound;

    @Column(name = "release_closing")
    private String releaseClosing;

    @Column(name = "found_state")
    private String foundState;

    @Column(name = "is_blocking")
    private String isBlocking;

    @Column(name = "status")
    private String status;

    @Column(name = "severity")
    private String severity;

    @Column(name = "description")
    private String description;
}

