create table if not exists shiro.action
(
    id int auto_increment
        primary key,
    action_code varchar(255) not null,
    action_name varchar(255) not null
);

create table if not exists shiro.module
(
    id int auto_increment
        primary key,
    module_code varchar(255) not null,
    module_name varchar(255) not null
);

create table if not exists shiro.permission
(
    id int auto_increment
        primary key,
    permission_name varchar(255) not null,
    module_id int not null,
    action_id int not null
);

create table if not exists shiro.role
(
    id int auto_increment
        primary key,
    role_name varchar(255) not null,
    permissions json not null
);

create table if not exists shiro.user
(
    role json not null,
    id int auto_increment
        primary key,
    name varchar(255) not null,
    password varchar(255) not null,
    constraint user_name_uindex
        unique (name)
);

