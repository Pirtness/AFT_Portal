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
@Table(name = "test_runs_and_automation_review")
public class RunsAndAutomationReview {

    @Id
    @Column(name = "id_test_group")
    private Long idTestGroup;

    @Column(name = "total_alm_tests_amount")
    private Long totalAlmTestsAmount;

    @Column(name = "automated_tests_amount")
    private Long automatedTestsAmount;

    @Column(name = "new_tests_in_pr_amount")
    private Long newTestsInPrAmount;

    @Column(name = "failed_tests_amount")
    private Long failedTestsAmount;

    @Column(name = "passed_tests_amount")
    private Long passedTestsAmount;

    @Column(name = "build_name")
    private String buildName;

    @Column(name = "test_run_start_date")
    private Date testRunStartDate;

    @Column(name = "report_creation_date")
    private Date reportCreationDate;

    @Column(name = "description")
    private String description;

}
