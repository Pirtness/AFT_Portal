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
@Table(name = "test_folder")
public class TestFolder {

    @Id
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "parent_id")
    private long parentId;

    @Column(name = "last_modified")
    private String lastModified;

    @Column(name = "visible_order")
    private String visibleOrder;

}
