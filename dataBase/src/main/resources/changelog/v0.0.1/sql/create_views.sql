create or replace view vi_report_folder
            (id, parent_id, name, last_modified, visible_order, level, cnt_all, cnt_pass, cnt_fail,
             cnt_warn, cnt_impl, cnt_pr, cnt_defect)
as
WITH RECURSIVE
    first_folderes AS (
        SELECT tf.id,
               tf.name,
               tf.parent_id,
               tf.last_modified,
               tf.visible_order
        FROM portal.test_folder tf
        WHERE (EXISTS(SELECT t_1.id
                      FROM portal.test t_1
                      WHERE t_1.parent_id = tf.id))
    ),
    first_foleders_with_test AS (
        SELECT ff.id,
               ff.name,
               ff.parent_id,
               ff.last_modified,
               ff.visible_order,
               count(t_1.id)                             AS cnt_all,
               sum(
                       CASE
                           WHEN it.last_status::text = 'p'::text THEN 1
                           ELSE 0
                           END)                          AS cnt_pass,
               sum(
                       CASE
                           WHEN it.last_status::text = 'f'::text THEN 1
                           ELSE 0
                           END)                          AS cnt_fail,
               sum(
                       CASE
                           WHEN it.last_status::text = 'f'::text AND it.last_status::text <> it.prev_status::text THEN 1
                           ELSE 0
                           END)                          AS cnt_warn,
               count(it.id)                              AS cnt_impl,
               count(tpr.id)                             AS cnt_pr,
               count(t2d.id_test)                        AS cnt_defect
        FROM first_folderes ff
                 JOIN portal.test t_1 ON t_1.parent_id = ff.id
                 LEFT JOIN portal.report_test_runs it ON it.id = t_1.id
                 LEFT JOIN portal.test_in_pull_request tpr ON tpr.id = t_1.id
                 LEFT JOIN portal.test2defect t2d ON t_1.id = t2d.id_test
        GROUP BY ff.id, ff.parent_id, ff.name, ff.visible_order, ff.last_modified
    ),
    parent_folder(id, name, parent_id, last_modified, visible_order, cnt_all, cnt_pass, cnt_fail,
                  cnt_warn, cnt_impl, cnt_pr, cnt_defect, level) AS (
        SELECT ffwt.id,
               ffwt.name,
               ffwt.parent_id,
               ffwt.last_modified,
               ffwt.visible_order,
               ffwt.cnt_all,
               ffwt.cnt_pass,
               ffwt.cnt_fail,
               ffwt.cnt_warn,
               ffwt.cnt_impl,
               ffwt.cnt_pr,
               ffwt.cnt_defect,
               0::bigint AS int8
        FROM first_foleders_with_test ffwt
        UNION ALL
        SELECT tf.id,
               tf.name,
               tf.parent_id,
               tf.last_modified,
               tf.visible_order,
               pf.cnt_all,
               pf.cnt_pass,
               pf.cnt_fail,
               pf.cnt_warn,
               pf.cnt_impl,
               pf.cnt_pr,
               pf.cnt_defect,
               pf.level + 1
        FROM portal.test_folder tf
                 JOIN parent_folder pf ON pf.parent_id = tf.id
    )
SELECT t.id,
       t.parent_id,
       t.name,
       t.last_modified,
       t.visible_order,
       t.level,
       t.cnt_all::integer        AS cnt_all,
       t.cnt_pass::integer       AS cnt_pass,
       t.cnt_fail::integer       AS cnt_fail,
       t.cnt_warn::integer       AS cnt_warn,
       t.cnt_impl::integer       AS cnt_impl,
       t.cnt_pr::integer         AS cnt_pr,
       t.cnt_defect::integer     AS cnt_defect
FROM (SELECT pf.id,
             pf.name,
             pf.parent_id,
             pf.last_modified,
             pf.visible_order,
             max(pf.level)          AS level,
             sum(pf.cnt_all)        AS cnt_all,
             sum(pf.cnt_pass)       AS cnt_pass,
             sum(pf.cnt_fail)       AS cnt_fail,
             sum(pf.cnt_warn)       AS cnt_warn,
             sum(pf.cnt_impl)       AS cnt_impl,
             sum(pf.cnt_pr)         AS cnt_pr,
             sum(pf.cnt_defect)     AS cnt_defect
      FROM parent_folder pf
      GROUP BY pf.id, pf.name, pf.parent_id, pf.last_modified, pf.visible_order
      UNION ALL
      SELECT tf.id,
             tf.name,
             tf.parent_id,
             tf.last_modified,
             tf.visible_order,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             0
      FROM portal.test_folder tf
      WHERE NOT (EXISTS(SELECT rf.id
                        FROM parent_folder rf
                        WHERE rf.id = tf.id))) t;


create or replace view vi_report_test_runs
            (id, name, parent_id, id_last_run, last_build_name, last_status, prev_status, id_last_pass_run,
             last_pass_build_name, cnt_run, cnt_pass, cnt_fail)
as
WITH all_runs AS (
    SELECT max(trp.id_test_run) OVER w                                             AS last_run,
           first_value(trp.status) OVER w                                          AS last_status,
           nth_value(trp.status, 2) OVER w                                         AS prev_status,
           max(trp.id_test_run) FILTER (WHERE trp.status::text = 'p'::text) OVER w AS last_pass_run,
           trp.id_test_run,
           trp.id_alm_test,
           trp.status
    FROM portal.test_run_package trp
             JOIN portal.impl_test it ON it.id = trp.id_alm_test
    WHERE trp.is_root
        WINDOW w AS (PARTITION BY trp.id_alm_test ORDER BY trp.date_start DESC RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING)
),
     result_runs AS (
         SELECT ar.id_alm_test   AS id_test,
                ar.last_status,
                ar.last_run,
                ar.last_pass_run,
                ar.prev_status,
                count(*)         AS cnt_run,
                sum(
                        CASE
                            WHEN ar.status::text = 'p'::text THEN 1
                            ELSE 0
                            END) AS cnt_pass,
                sum(
                        CASE
                            WHEN ar.status::text = 'f'::text THEN 1
                            ELSE 0
                            END) AS cnt_fail
         FROM all_runs ar
         GROUP BY ar.last_run, ar.last_pass_run, ar.id_alm_test, ar.last_status, ar.prev_status
     )
SELECT t.id,
       t.name,
       t.parent_id,
       rr.last_run        AS id_last_run,
       tr_last.build_name AS last_build_name,
       rr.last_status,
       rr.prev_status,
       rr.last_pass_run   AS id_last_pass_run,
       tr_pass.build_name AS last_pass_build_name,
       rr.cnt_run,
       rr.cnt_pass,
       rr.cnt_fail
FROM result_runs rr
         JOIN portal.test t ON t.id = rr.id_test
         JOIN portal.test_run tr_last ON tr_last.id = rr.last_run
         LEFT JOIN portal.test_run tr_pass ON tr_pass.id = rr.last_pass_run;

create or replace view vi_web_report_folder
            (id, parent_id, name, last_modified, visible_order, level, cnt_all, cnt_pass, cnt_fail,
             cnt_warn, cnt_impl, cnt_pr, cnt_defect, type_op, last_change)
as
SELECT fr.id,
       fr.parent_id,
       fr.name,
       fr.last_modified,
       fr.visible_order::integer AS visible_order,
       fr.level,
       fr.cnt_all,
       fr.cnt_pass,
       fr.cnt_fail,
       fr.cnt_warn,
       fr.cnt_impl,
       fr.cnt_pr,
       fr.cnt_defect,
       NULL::text                AS type_op,
       NULL::timestamp          AS last_change
FROM portal.report_folder fr
GROUP BY fr.id, fr.parent_id, fr.name, fr.last_modified, fr.visible_order, fr.level, fr.cnt_all,
         fr.cnt_pass, fr.cnt_fail, fr.cnt_warn, fr.cnt_impl, fr.cnt_pr, fr.cnt_defect;

create or replace view vi_web_test
            (id, name, parent_id, level, zni, status, author, visible_order, last_modified, creation_time,
             is_automatised, regress_order, author_autotest, type_op, last_change, defects)
as
SELECT t.id,
       t.name,
       t.parent_id,
       t.level,
       t.zni,
       t.status,
       t.author,
       t.visible_order,
       t.last_modified,
       t.creation_time,
       t.is_automatised,
       t.regress_order,
       t.author_autotest,
       NULL::text                                                  AS type_op,
       NULL::timestamp                                        AS last_change,
       array_to_string(ARRAY(SELECT t2d.id_defect
                             FROM portal.test2defect t2d
                             WHERE t2d.id_test = t.id), ','::text) AS defects
FROM portal.test t;

create view vi_web_report_defect (release_found, cnt_critical, cnt_very_important, cnt_high, cnt_medium, cnt_low) as
SELECT t.release_found,
       sum(
               CASE
                   WHEN t.severity = '01-Критический'::text THEN 1
                   ELSE 0
                   END)::integer AS cnt_critical,
       sum(
               CASE
                   WHEN t.severity = '02-Очень важный'::text THEN 1
                   ELSE 0
                   END)::integer AS cnt_very_important,
       sum(
               CASE
                   WHEN t.severity = '03-Высокий'::text THEN 1
                   ELSE 0
                   END)::integer AS cnt_high,
       sum(
               CASE
                   WHEN t.severity = '04-Средний'::text THEN 1
                   ELSE 0
                   END)::integer AS cnt_medium,
       sum(
               CASE
                   WHEN t.severity = '05-Низкий'::text THEN 1
                   ELSE 0
                   END)::integer AS cnt_low
FROM portal.defect t
GROUP BY t.release_found
ORDER BY t.release_found;