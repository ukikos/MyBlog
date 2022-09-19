create table if not exists comments (
    id int8 generated by default as identity,
    content varchar(500),
    post_id int8,
    user_id int8,
    primary key (id)
);

create table if not exists likes (
    id int8 generated by default as identity,
    post_id int8,
    user_id int8,
    primary key (id)
);

create table if not exists posts (
    id int8 generated by default as identity,
    content varchar(500),
    privacy varchar(64),
    user_id int8,
    primary key (id)
);

create table if not exists roles (
    id int8 generated by default as identity,
    role varchar(64),
    primary key (id)
);

create table if not exists statuses (
    id int8 generated by default as identity,
    status varchar(64),
    primary key (id)
);

create table if not exists subscriptions (
    id int8 generated by default as identity,
    user_id int8,
    subscription_id int8,
    primary key (id)
);

create table if not exists users (
    id int8 generated by default as identity,
    username varchar(255),
    password varchar(255),
    role_id int8,
    status_id int8,
    primary key (id)
);

alter table if exists comments
    drop constraint if exists FKh4c7lvsc298whoyd4w9ta25cr;

alter table if exists comments
    drop constraint if exists FK8omq0tc18jd43bu5tjh6jvraq;

alter table if exists likes
    drop constraint if exists FKry8tnr4x2vwemv2bb0h5hyl0x;

alter table if exists likes
    drop constraint if exists FKnvx9seeqqyy71bij291pwiwrg;

alter table if exists posts
    drop constraint if exists FK5lidm6cqbc7u4xhqpxm898qme;

alter table if exists subscriptions
    drop constraint if exists FK2g9ufo6t52ndy9vo3o5o2wh4f;

alter table if exists subscriptions
    drop constraint if exists FKhro52ohfqfbay9774bev0qinr;

alter table if exists users
    drop constraint if exists FKp56c1712k691lhsyewcssf40f;

alter table if exists users
    drop constraint if exists FKpp127ea8ef1s2x9em2o3bhsvo;




alter table if exists comments
    add constraint FKh4c7lvsc298whoyd4w9ta25cr
    foreign key (post_id) references posts;

alter table if exists comments
    add constraint FK8omq0tc18jd43bu5tjh6jvraq
    foreign key (user_id) references users;

alter table if exists likes
    add constraint FKry8tnr4x2vwemv2bb0h5hyl0x
    foreign key (post_id) references posts;

alter table if exists likes
    add constraint FKnvx9seeqqyy71bij291pwiwrg
    foreign key (user_id) references users;

alter table if exists posts
    add constraint FK5lidm6cqbc7u4xhqpxm898qme
    foreign key (user_id) references users;

alter table if exists subscriptions
    add constraint FK2g9ufo6t52ndy9vo3o5o2wh4f
    foreign key (subscription_id) references users;

alter table if exists subscriptions
    add constraint FKhro52ohfqfbay9774bev0qinr
    foreign key (user_id) references users;

alter table if exists users
    add constraint FKp56c1712k691lhsyewcssf40f
    foreign key (role_id) references roles;

alter table if exists users
    add constraint FKpp127ea8ef1s2x9em2o3bhsvo
    foreign key (status_id) references statuses;