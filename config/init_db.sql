create table public.resume
(
    uuid      char(36) primary key not null,
    full_name text                 not null
);

create table public.contact
(
    id          serial primary key,
    resume_uuid char(36) not null references resume (uuid) on delete cascade,
    type        text     not null,
    value       text     not null
);

create unique index contact_uuid_type_index on contact (resume_uuid, type);

create table public.section
(
    id          serial primary key,
    resume_uuid char(36) not null references resume (uuid) on delete cascade,
    type        text     not null,
    value       text     not null
);

create unique index section_uuid_type_index on contact (resume_uuid, type);