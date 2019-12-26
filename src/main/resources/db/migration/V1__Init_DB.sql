create sequence hibernate_sequence start 1 increment 1;

create table role (
    user_id varchar(36) not null,
    roles varchar(255)
);
create table ticket (
    id varchar(36) not null,
    accepted boolean not null,
    comment varchar(255),
    completion_date timestamp,
    creation_date timestamp not null,
    description varchar(255) not null,
    from_ads boolean not null,
    number serial not null,
    status VARCHAR(255) DEFAULT 'OPEN' not null,
    name varchar(255) not null,
    manager_id varchar(36),
    operator_id varchar(36),
    recipient_id varchar(36),
    sender_id varchar(36),
    primary key (id)
);
create table usr (
    id varchar(36) not null,
    activation_code varchar(255),
    creation_date timestamp not null,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    middle_name varchar(255),
    password varchar(255),
    type_user varchar(255),
    username varchar(255) not null,
    primary key (id)
);
alter table if exists ticket add constraint UK_2q957ve7gyajshqxiaga68a0w
    unique (number);
alter table if exists role add constraint FKghqm2pia0ngnqyt92adfhq26d
    foreign key (user_id) references usr;
alter table if exists ticket add constraint FK3xaifs6tkrvd7jn7bok3ph0je
    foreign key (manager_id) references usr;
alter table if exists ticket add constraint FKe6nb4721vcbigdt174p2h67fn
    foreign key (operator_id) references usr;
alter table if exists ticket add constraint FKno7jm2c9qsf1ce7gpu9cmyay8
    foreign key (recipient_id) references usr;
alter table if exists ticket add constraint FKoyv8f2pegmoofk3nfb8cvbcuc
    foreign key (sender_id) references usr;