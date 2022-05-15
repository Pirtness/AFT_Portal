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
@Table(name = "vi_web_test")
public class WebTest {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "parent_id")
    private long parentId;

    @Column(name = "level")
    private String level;

    @Column(name = "zni")
    private String zni;

    @Column(name = "status")
    private String status;

    @Column(name = "author")
    private String author;

    @Column(name = "visible_order")
    private String visibleOrder;

    @Column(name = "last_modified")
    private String lastModified;

    @Column(name = "creation_time")
    private String creationTime;

    @Column(name = "is_automatised")
    private String isAutomatised;

    @Column(name = "regress_order")
    private String regressOrder;

    @Column(name = "author_autotest")
    private String authorAutotest;

    @Column(name = "type_op")
    private String typeOp;

    @Column(name = "last_change")
    private java.sql.Timestamp lastChange;

    @Column(name = "defects")
    private String defects;
}
