create table if not exists test_folder
(
    id            bigint not null
        constraint test_folder_pkey
            primary key,
    name          text,
    parent_id     bigint
        constraint test_folder_test_folder_fk
            references test_folder
            deferrable,
    last_modified text,
    visible_order text
);

create table if not exists defect
(
    id               bigint not null
        constraint defect_pkey
            primary key,
    name             text,
    date_create      timestamp,
    type_defect      text,
    release_found    text,
    is_blocking      text,
    release_closing  text,
    status           text,
    found_state      text,
    severity         text,
    last_modified    timestamp,
    description      text
);

create table if not exists report_test_runs
(
    id                   bigint  not null,
    name                 text,
    parent_id            bigint,
    id_last_run          bigint,
    last_build_name      varchar(200),
    last_status          varchar,
    prev_status          varchar,
    id_last_pass_run     bigint,
    last_pass_build_name varchar(200),
    cnt_run              bigint,
    cnt_pass             bigint,
    cnt_fail             bigint,
    constraint report_test_runs_pk
        primary key (id)
);

create table if not exists impl_test
(
    id             bigint not null
        constraint impl_test_pkey
            primary key,
    tags           text,
    relative_path  text,
    first_author   varchar,
    first_date     bigint,
    last_date      bigint,
    last_committer varchar
);

create table if not exists test
(
    id              bigint not null
        constraint test_pkey
            primary key,
    name            text,
    parent_id       bigint
        constraint design_step_test_fk
            references test_folder
            deferrable,
    level           text,
    zni             text,
    status          text,
    author          text,
    visible_order   text,
    last_modified   text,
    creation_time   text,
    is_automatised  text,
    regress_order   text,
    author_autotest text,
    aft_status      varchar
);

create table if not exists test2defect
(
    id_test   bigint
        constraint test2defect_test_fk
            references test
            deferrable,
    id_defect bigint
        constraint test2defect_defect_fk
            references defect
            deferrable
);

create table if not exists design_step
(
    id                bigint not null
        constraint design_step_pkey
            primary key,
    name              text,
    parent_id         bigint
        constraint design_step_test_fk
            references test
            deferrable,
    visible_order     text,
    version_timestamp text,
    version           text,
    attachment        text,
    has_params        text,
    expected          text,
    description       text
);

create table if not exists report_folder
(
    id             bigint not null
        constraint report_folder_pk
            primary key,
    parent_id      bigint
        constraint report_folder_report_folder_id_fk
            references report_folder
            deferrable,
    name           text,
    last_modified  text,
    visible_order  text,
    level          bigint,
    cnt_all        integer,
    cnt_pass       integer,
    cnt_fail       integer,
    cnt_warn       integer,
    cnt_impl       integer,
    cnt_pr         integer,
    cnt_defect     integer
);


create table if not exists test_groups
(
    id   bigint  not null
        constraint test_runs_and_automation_review_pk
            primary key,
    name varchar not null
);

create table if not exists test_in_pull_request
(
    id          bigint not null
        constraint test_in_pull_request_pkey
            primary key,
    id_pr       integer,
    version     text,
    change_type varchar,
    branch_from varchar,
    branch_to   varchar
);

create sequence if not exists test_run_seq;

create table if not exists test_run
(
    id          bigint    default nextval('test_run_seq') not null
        constraint test_runs_pk
            primary key,
    build_name  varchar(200)                                                  not null,
    build_date  timestamp                                                     not null,
    start_date  timestamp default now(),
    end_date    timestamp,
    stand       varchar(50)                                                   not null,
    description varchar(500)
);

create sequence if not exists test_run_package_seq;
create table if not exists test_run_package
(
    id                bigint default nextval('test_run_package_seq') not null
        constraint test_run_packages_pk
            primary key,
    id_test_run       bigint                                                             not null
        constraint test_runs_fk
            references test_run,
    id_alm_test       bigint                                                             not null,
    id_parent_package bigint
        constraint test_run_package_fk
            references test_run_package,
    run_order         integer,
    caption           varchar(300)                                                       not null,
    status            varchar(1)                                                         not null
        constraint test_run_package_status
            check ((status)::text = ANY
                   ((ARRAY ['p'::character varying, 'f'::character varying, 's'::character varying])::text[])),
    date_start        timestamp,
    duration          bigint                                                             not null,
    error_exists      boolean                                                            not null,
    type              varchar(1)                                                         not null
        constraint test_run_package_type
            check ((type)::text = ANY ((ARRAY ['f'::character varying, 's'::character varying])::text[])),
    is_root           boolean                                                            not null,
    description       varchar(500)
);

create sequence if not exists test_run_step_seq;
create table if not exists test_run_step
(
    id                        bigint default nextval('test_run_step_seq') not null
        constraint test_run_step_pkey
            primary key,
    id_test_run_package       bigint                                                             not null
        constraint test_run_package_fk
            references test_run_package,
    caption                   text                                                               not null,
    status                    varchar(1)                                                         not null
        constraint test_run_package_status
            check ((status)::text = ANY
                   ((ARRAY ['p'::character varying, 'f'::character varying, 's'::character varying])::text[])),
    run_order                 integer                                                            not null,
    date_start                timestamp                                                          not null,
    duration                  bigint                                                             not null,
    error_exists              boolean                                                            not null,
    error                     text,
    doc                       text,
    step_table                json,
    id_child_test_run_package bigint
        constraint test_run_package_child_fk
            references test_run_package
            deferrable initially deferred
);

create sequence if not exists test_run_step_embed_seq;
create table if not exists test_run_step_embed
(
    id               bigint default nextval('test_run_step_embed_seq') not null
        constraint test_run_step_embed_pkey
            primary key,
    id_test_run_step bigint
        constraint test_run_step_fk
            references test_run_step,
    embed            bytea                                                                 not null,
    mime_type        varchar(100)                                                          not null
);

create table if not exists test_runs_and_automation_review
(
    id_test_group          bigint not null
        constraint test_runs_and_automation_review_pk_2
            primary key
        constraint test_runs_and_automation_review_test_groups_id_fk
            references test_groups
            on delete cascade,
    total_alm_tests_amount bigint    default 0,
    automated_tests_amount bigint    default 0,
    new_tests_in_pr_amount bigint    default 0,
    failed_tests_amount    bigint    default 0,
    passed_tests_amount    bigint    default 0,
    build_name             varchar,
    test_run_start_date    timestamp,
    report_creation_date   timestamp default CURRENT_TIMESTAMP,
    description            varchar(500)
);