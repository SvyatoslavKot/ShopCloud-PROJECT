DROP TABLE  IF EXISTS users;
create TABLE users (
                       id SERIAL primary key ,
                       name varchar(255),
                       e_mail varchar(255),
                       password varchar(255),
                       archive boolean,
                       role varchar(255),
                       bucket_id BIGINT
                       --foreign key (bucket_id) references buckets (id)
);













