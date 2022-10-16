alter table store_license
    drop column license;

alter table store_license
    drop column license_from;

alter table store_license
    drop column registry;

alter table store_license
    drop column registry_from;

alter table store_license
    drop column registered_by;

alter table store_license
    alter column unp drop not null;
