package dataBase.entity.test;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Immutable
@Table(name ="vi_report_test_runs", schema = "portal")
public class WebReportTestLastRun {
    @Id
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "name")
    private String name;

    @Column(name = "last_build_name")
    private String lastBuildName;

    @Column(name = "last_status")
    private String lastStatus;

    @Column(name = "prev_status")
    private String prevStatus;

    @Column(name = "id_last_pass_run")
    private Long idLastPassRun;

    @Column(name = "last_pass_build_name")
    private String lastPassBuildName;

    @Column(name = "cnt_run")
    private Long cntRun;

    @Column(name = "cnt_pass")
    private Long cntPass;

    @Column(name = "cnt_fail")
    private Long cntFail;

    @Column(name = "id_last_run")
    private Long idLastRun;
}
