create table account_password_reset_token
(
    id         bigserial
        constraint pk_account_password_reset_token primary key,
    account_id bigint
        constraint fk_account_id references account,
    expiry_at  timestamp with time zone not null,
    token      varchar(255)             not null
);
