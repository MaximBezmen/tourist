create type WEEK_DAY as enum ('MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY', 'SATURDAY','SUNDAY');

create table store
(
    id               bigserial
        constraint pk_store primary key,
    account_id       bigint
        constraint fk_account_id references account unique,
    name             varchar(255) not null,
    about            text,
    url              varchar(255) not null unique,
    phones           text[],
    header_image     text,
    background_image text,
    footer_image     text,
    request_info     text
);

create table work_time
(
    id       bigserial
        constraint pk_work_time primary key,
    week_day WEEK_DAY not null,
    open     timestamp with time zone,
    close    timestamp with time zone,
    store_id bigserial
        constraint fk_store_id references store
);

create table store_license
(
    id            bigserial
        constraint pk_store_license primary key,
    unp           varchar(20) not null,
    license       varchar(20),
    license_from  date,
    registry      varchar(20),
    registry_from date,
    registered_by varchar(255),
    store_id      bigserial
        constraint fk_store_id references store
);
create table store_address
(
    id       bigserial
        constraint pk_store_address primary key,
    city     varchar(40) not null,
    street   varchar(70) not null,
    house    varchar(40),
    block    varchar(40),
    store_id bigserial
        constraint fk_store_id references store
);

create table social_network
(
    id        bigserial
        constraint pk_social_network primary key,
    vk        varchar(255),
    instagram varchar(255),
    facebook  varchar(255),
    store_id  bigserial
        constraint fk_store_id references store
);


