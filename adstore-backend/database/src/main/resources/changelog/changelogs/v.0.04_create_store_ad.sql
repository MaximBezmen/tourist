create table store_ad
(
    id            bigserial
        constraint pk_store_ad primary key,
    store_id      bigint
        constraint fk_store_id references store,
    title         varchar(255)             not null,
    description   text,
    images        text[],
    price         double precision,
    ip_address    varchar(255)             not null,
    location_info varchar(255),
    create_at     timestamp with time zone not null,
    is_active     boolean default true,
    is_deleted    boolean default false,
    delete_at     timestamp with time zone,
    request_info  text
);


