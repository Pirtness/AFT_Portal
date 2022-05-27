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
@Table(name="vi_report_folder")
public class TestFolderReport {
    @Id
    private Long id;

    @Column(name="parent_id")
    private Long parentId;

    @Column(name="name")
    private String name;

    @Column(name="last_modified")
    private String lastModified;

    @Column(name="visible_order")
    private String visibleOrder;

    @Column(name="level")
    private Long level;

    @Column(name="cnt_all")
    private Integer cntAll;

//    @Column(name="cnt_alm_web")
//    private Integer cntAlmWeb;
//
//    @Column(name="cnt_alm_automatised")
//    private Integer cntAlmAutomatised;

    @Column(name="cnt_impl")
    private Integer cntImpl;

    @Column(name = "cnt_pr")
    private Integer cntPr;

    @Column(name="cnt_defect")
    private Integer cntDefects;

}
