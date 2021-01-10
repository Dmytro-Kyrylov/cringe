create table "user"
(
    id       uuid
        constraint user_pk primary key,
    username text not null,
    password text not null,
    unique (username)
);

insert into "user"
values ('a5f51c1f-3f17-4939-9a10-3485e1f54cb0', 'Vadim',
        '$2y$10$AL3b8y.IGbzLXh2GVy93sekv4qt.dL2Q9I26CZT4bsbVM9LawwMdC');
insert into "user"
values ('1346dfab-9040-4d2c-a9c7-f1244c0f5329', 'Dima',
        '$2a$10$hnuKxobvGeqktIGW5pu5JuxIcNHEAT/VGOlav64m3/ijmjPY65HUq');

create table list
(
    id          uuid
        constraint list_pk primary key,
    created_by  uuid      not null
        constraint list_user_create_fk references "user",
    created_at  timestamp not null default current_timestamp,
    updated_by  uuid      not null
        constraint list_user_update_fk references "user",
    updated_at  timestamp not null default current_timestamp,
    qualifier   bigint    not null,
    title       text      not null,
    description text,
    deleted     boolean   not null default false
);

create sequence list_qualifier_seq;

create table list_record
(
    id             uuid
        constraint list_record_pk primary key,
    created_by     uuid      not null
        constraint list_record_user_create_fk references "user",
    created_at     timestamp not null default current_timestamp,
    updated_by     uuid      not null
        constraint list_record_user_update_fk references "user",
    updated_at     timestamp not null default current_timestamp,
    list_qualifier bigint    not null,
    body           text      not null,
    deleted        boolean   not null default false
);

create table list_record_reaction
(
    user_id        uuid   not null
        constraint list_record_reaction_user_fk references "user",
    list_record_id uuid   not null
        constraint list_record_reaction_list_record_fk references list_record,
    rating         bigint not null default 0,
    constraint list_record_reaction_pk primary key (user_id, list_record_id)
);
