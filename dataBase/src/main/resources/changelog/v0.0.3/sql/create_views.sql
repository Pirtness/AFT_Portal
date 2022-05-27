create or replace view vi_test_runs_and_automation_review
            (id_test_group, total_alm_tests_amount, automated_tests_amount, new_tests_in_pr_amount, failed_tests_amount,
             passed_tests_amount, build_name, test_run_start_date, report_creation_date, description)
as
(WITH runs AS (
    SELECT rtr.id,
           rtr.name,
           rtr.parent_id,
           rtr.id_last_run,
           rtr.last_build_name,
           rtr.last_status,
           rtr.prev_status,
           rtr.id_last_pass_run,
           rtr.last_pass_build_name,
           rtr.cnt_run,
           rtr.cnt_pass,
           rtr.cnt_fail,
           it.id,
           it.tags,
           it.relative_path,
           it.first_author,
           it.first_date,
           it.last_date,
           it.last_committer
    FROM portal.report_test_runs rtr
             JOIN portal.impl_test it ON it.id = rtr.id
    WHERE it.tags ~~ '%ui%'::text
),
      last_run AS (
          SELECT tr.id,
                 tr.build_name,
                 tr.end_date,
                 tr.description
          FROM portal.test_run tr
          WHERE tr.id = ((SELECT max(sr.id_last_run) AS max
                          FROM runs sr(id, name, parent_id, id_last_run, last_build_name, last_status, prev_status,
                                       id_last_pass_run, last_pass_build_name, cnt_run, cnt_pass, cnt_fail,
                                       id_1, tags, relative_path, first_author, first_date,
                                       last_date, last_committer)))
      )
 SELECT (SELECT tg.id
         FROM portal.test_groups tg
         WHERE tg.name::text = 'UI'::text)     AS id_test_group,
        (SELECT 100)                          AS total_alm_tests_amount,
        (SELECT count(*) AS count
         FROM portal.impl_test it
         WHERE it.tags ~~ '%ui%'::text)        AS automated_tests_amount,
        0                                        AS new_tests_in_pr_amount,
        (SELECT count(*) AS count
         FROM runs sr(id, name, parent_id, id_last_run, last_build_name, last_status, prev_status,
                      id_last_pass_run, last_pass_build_name, cnt_run, cnt_pass, cnt_fail, id_1,
                      tags, relative_path, first_author, first_date, last_date, last_committer)
         WHERE sr.last_status::text = 'f'::text) AS failed_tests_amount,
        (SELECT count(*) AS count
         FROM runs sr(id, name, parent_id, id_last_run, last_build_name, last_status, prev_status,
                      id_last_pass_run, last_pass_build_name, cnt_run, cnt_pass, cnt_fail, id_1,
                      tags, relative_path, first_author, first_date, last_date, last_committer)
         WHERE sr.last_status::text = 'p'::text) AS passed_tests_amount,
        lsr.build_name,
        lsr.end_date                             AS test_run_start_date,
        timezone('Europe/Moscow'::text, now())   AS report_creation_date,
        lsr.description
 FROM last_run lsr)

UNION ALL

(WITH runs AS (
    SELECT rtr.id,
           rtr.name,
           rtr.parent_id,
           rtr.id_last_run,
           rtr.last_build_name,
           rtr.last_status,
           rtr.prev_status,
           rtr.id_last_pass_run,
           rtr.last_pass_build_name,
           rtr.cnt_run,
           rtr.cnt_pass,
           rtr.cnt_fail,
           it.id,
           it.tags,
           it.relative_path,
           it.first_author,
           it.first_date,
           it.last_date,
           it.last_committer
    FROM portal.report_test_runs rtr
             JOIN portal.impl_test it ON it.id = rtr.id
    WHERE it.tags ~~ '%api%'::text
),
      last_run AS (
          SELECT tr.id,
                 tr.build_name,
                 tr.end_date,
                 tr.description
          FROM portal.test_run tr
          WHERE tr.id = ((SELECT max(sr.id_last_run) AS max
                          FROM runs sr(id, name, parent_id, id_last_run, last_build_name, last_status, prev_status,
                                       id_last_pass_run, last_pass_build_name, cnt_run, cnt_pass, cnt_fail,
                                       id_1, tags, relative_path, first_author, first_date,
                                       last_date, last_committer)))
      )
 SELECT (SELECT tg.id
         FROM portal.test_groups tg
         WHERE tg.name::text = 'API'::text)     AS id_test_group,
        (SELECT 100)                          AS total_alm_tests_amount,
        (SELECT count(*) AS count
         FROM portal.impl_test it
         WHERE it.tags ~~ '%api%'::text)        AS automated_tests_amount,
        0                                        AS new_tests_in_pr_amount,
        (SELECT count(*) AS count
         FROM runs sr(id, name, parent_id, id_last_run, last_build_name, last_status, prev_status,
                      id_last_pass_run, last_pass_build_name, cnt_run, cnt_pass, cnt_fail, id_1,
                      tags, relative_path, first_author, first_date, last_date, last_committer)
         WHERE sr.last_status::text = 'f'::text) AS failed_tests_amount,
        (SELECT count(*) AS count
         FROM runs sr(id, name, parent_id, id_last_run, last_build_name, last_status, prev_status,
                      id_last_pass_run, last_pass_build_name, cnt_run, cnt_pass, cnt_fail, id_1,
                      tags, relative_path, first_author, first_date, last_date, last_committer)
         WHERE sr.last_status::text = 'p'::text) AS passed_tests_amount,
        lsr.build_name,
        lsr.end_date                             AS test_run_start_date,
        timezone('Europe/Moscow'::text, now())   AS report_creation_date,
        lsr.description
 FROM last_run lsr)


UNION ALL

(WITH runs AS (
    SELECT rtr.id,
           rtr.name,
           rtr.parent_id,
           rtr.id_last_run,
           rtr.last_build_name,
           rtr.last_status,
           rtr.prev_status,
           rtr.id_last_pass_run,
           rtr.last_pass_build_name,
           rtr.cnt_run,
           rtr.cnt_pass,
           rtr.cnt_fail,
           it.id,
           it.tags,
           it.relative_path,
           it.first_author,
           it.first_date,
           it.last_date,
           it.last_committer
    FROM portal.report_test_runs rtr
             JOIN portal.impl_test it ON it.id = rtr.id
    WHERE it.tags ~~ '%quality_gate%'::text
),
      last_run AS (
          SELECT tr.id,
                 tr.build_name,
                 tr.end_date,
                 tr.description
          FROM portal.test_run tr
          WHERE tr.id = ((SELECT max(sr.id_last_run) AS max
                          FROM runs sr(id, name, parent_id, id_last_run, last_build_name, last_status, prev_status,
                                       id_last_pass_run, last_pass_build_name, cnt_run, cnt_pass, cnt_fail,
                                       id_1, tags, relative_path, first_author, first_date,
                                       last_date, last_committer)))
      )
 SELECT (SELECT tg.id
         FROM portal.test_groups tg
         WHERE tg.name::text = 'Quality Gate'::text)     AS id_test_group,
        (SELECT 100)                          AS total_alm_tests_amount,
        (SELECT count(*) AS count
         FROM portal.impl_test it
         WHERE it.tags ~~ '%quality_gate%'::text)        AS automated_tests_amount,
        0                                        AS new_tests_in_pr_amount,
        (SELECT count(*) AS count
         FROM runs sr(id, name, parent_id, id_last_run, last_build_name, last_status, prev_status,
                      id_last_pass_run, last_pass_build_name, cnt_run, cnt_pass, cnt_fail, id_1,
                      tags, relative_path, first_author, first_date, last_date, last_committer)
         WHERE sr.last_status::text = 'f'::text) AS failed_tests_amount,
        (SELECT count(*) AS count
         FROM runs sr(id, name, parent_id, id_last_run, last_build_name, last_status, prev_status,
                      id_last_pass_run, last_pass_build_name, cnt_run, cnt_pass, cnt_fail, id_1,
                      tags, relative_path, first_author, first_date, last_date, last_committer)
         WHERE sr.last_status::text = 'p'::text) AS passed_tests_amount,
        lsr.build_name,
        lsr.end_date                             AS test_run_start_date,
        timezone('Europe/Moscow'::text, now())   AS report_creation_date,
        lsr.description
 FROM last_run lsr);