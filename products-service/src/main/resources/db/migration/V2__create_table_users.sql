create sequence users_id_seq;

create table users
(
    id       bigint default nextval('users_id_seq') primary key,
    username varchar(255) unique
);

insert into users
(username)
values
('Петя'),
('Вася'),
('Иван'),
('Жорик');



