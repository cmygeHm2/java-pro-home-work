create sequence products_id_seq;

create table products
(
    id bigint default nextval('products_id_seq') primary key,
    account_number varchar(32) unique,
    balance      numeric(18, 2),
    product_type   varchar(255),
    user_id        bigint
);

alter table products
add constraint fk_user_id
foreign key (user_id)
references users(id);

insert into products
(account_number, balance, product_type, user_id)
values
    ('1111-0000', 500, 'Save', 1),
    ('1111-2522', 5114, 'Credit', 1),
    ('2222-4512', 6544, 'Credit', 2),
    ('3333-7984', 421, 'Mortgage', 3);
