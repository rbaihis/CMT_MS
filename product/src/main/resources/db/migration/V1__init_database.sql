create table if not exists category
(
    id          bigint not null
    primary key,
    description varchar(255),
    name        varchar(255) unique
    );

create table if not exists product
(
    id                 bigint          not null
    primary key,
    available_quantity bigint not null,
    description        varchar(255),
    name               varchar(255),
    price              numeric(38, 2),
    version            bigint default 0,
    category_id        bigint not null
    constraint fk1mtsbur82frn64de7balymq9s
    references category
    );

create sequence if not exists category_seq increment by 50;
create sequence if not exists product_seq increment by 50;