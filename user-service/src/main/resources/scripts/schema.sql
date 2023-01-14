DROP TABLE IF EXISTS users;

create table users(
    id  bigint not null AUTO_INCREMENT,
    email   varchar(100),
    username varchar(50),
    password varchar(500),
    profile_url varchar(500),
    created_at timestamp default NOW(),
    updated_at timestamp default NOW(),
    primary key (id)
)