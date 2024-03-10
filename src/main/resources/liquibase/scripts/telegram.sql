-- liquibase formatted sql

-- changeset vpotapov:1
create table tasks (
id bigserial primary key,
chat_id bigint not null,
text varchar(255) not null,
date_and_time timestamp not null
);




