create table error_template
(
    id                    bigint not null
        constraint error_templates_pk
            primary key,
    error_template        bytea  not null,
    hashed_error_template bytea  not null,
    description           varchar
);

create unique index error_templates_id_uindex
    on error_template (id);


create table report_failed_test_to_error_template
(
    id            bigserial not null
        constraint report_failed_test_to_error_template_pk
            primary key,
    id_test       bigint    not null,
    error_text    bytea     not null,
    id_template   bigint
        constraint report_failed_test_to_error_template_error_templates_id_fk
            references error_template,
    creation_time timestamp default now()
);

-- grant select, update on sequence report_failed_test_to_error_template_id_seq to tests_app;

create unique index report_failed_test_to_error_template_id_uindex
    on report_failed_test_to_error_template (id);

