create database cmygehm_db;
\connect cmygehm_db;

create schema if not exists homework_4;
create table homework_4.users
(
    id       bigserial primary key,
    username varchar(255) unique
);
