create table user (
    user_id int primary key auto_increment ,
    username varchar(50) not null unique,
password varchar(100) not null
);
create table actor_type (
    type_id int primary key auto_increment,
    type_name varchar(50) not null unique
);
create table actor (
    actor_id int primary key auto_increment,
    type_id int,
    name varchar(50) not null,
    gender enum('男', '女', '其他'),
    age int,
    birth_place varchar(100),
    foreign key (type_id) references actor_type(type_id)
);

create table contract (
    contract_id int primary key auto_increment,
    actor_id int not null,
    sign_start date not null,
    sign_end date not null,
    create_time datetime default current_timestamp,
    update_time datetime default current_timestamp on update current_timestamp,
    foreign key (actor_id) references actor(actor_id)
);

create table actor_award (
    actor_id int not null,
    award_id int not null,
    primary key (actor_id, award_id),
    foreign key (actor_id) references actor(actor_id),
    foreign key (award_id) references award(award_id)
);

create table award (
    award_id int primary key auto_increment,
    award_name varchar(100) not null,
    award_year year not null
);

create table work (
    work_id int primary key auto_increment,
    work_name varchar(100) not null,
    release_date date,
    director varchar(50),
    genre varchar(20)
);

create table skill (
    skill_id int primary key auto_increment,
    skill_name varchar(50) not null unique
);

create table contact_info (
    contact_id int primary key auto_increment,
    actor_id int not null unique,
    phone varchar(20),
    email varchar(100) unique,
    address text,
    foreign key (actor_id) references actor(actor_id)
);

create table actor_skill (
    actor_id int not null,
    skill_id int not null,
    primary key (actor_id, skill_id),
    foreign key (actor_id) references actor(actor_id),
    foreign key (skill_id) references skill(skill_id)
);

create table actor_work (
    actor_id int not null,
    work_id int not null,
    primary key (actor_id, work_id),
    foreign key (actor_id) references actor(actor_id),
    foreign key (work_id) references work(work_id)
);

insert into user(user_id,username,password) values(001,'admin','123456');

ALTER TABLE `user` ADD COLUMN `role` varchar(20) not null DEFAULT 'ACTOR';
UPDATE `user` SET `role` = 'ADMIN' WHERE `username` = 'admin';

