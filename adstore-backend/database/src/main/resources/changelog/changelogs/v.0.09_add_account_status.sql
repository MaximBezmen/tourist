create type ACCOUNT_VERIFICATION_STATUS as enum ('VERIFIED','BLOCKED','INITIALIZED');
COMMENT ON TYPE ACCOUNT_VERIFICATION_STATUS IS 'Status of account.';

alter table account_verification
    drop column verified;

alter table account_verification
    add status ACCOUNT_VERIFICATION_STATUS default 'INITIALIZED' not null;


