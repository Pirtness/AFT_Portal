package dataBase.entity.test;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Immutable
@Table(name ="vi_report_folder")
public class WebReportFolder {

  @Id
  private Long id;

  @Column(name = "parent_id")
  private Long parentId;

  @Column(name = "name")
  private String name;

  @Column(name = "last_modified")
  private String lastModified;

  @Column(name = "visible_order")
  private Integer visibleOrder;

  @Column(name = "level")
  private Long level;

  @Column(name = "cnt_all")
  private Integer cntAll;

  @Column(name = "cnt_pass")
  private Integer cntPass;

  @Column(name = "cnt_fail")
  private Integer cntFail;

  @Column(name = "cnt_warn")
  private Integer cntWarn;

  @Column(name = "cnt_impl")
  private Integer cntImpl;

  @Column(name = "cnt_pr")
  private Integer cntPr;

  @Column(name = "cnt_defect")
  private Integer cntDefect;

//  @Column(name = "type_op")
//  private String typeOp;
//
//  @Column(name = "last_change")
//  private java.sql.Timestamp lastChange;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    WebReportFolder that = (WebReportFolder) o;

    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return 1185635501;
  }
}
