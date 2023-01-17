DROP TABLE  IF EXISTS users;
create TABLE users(
                      id SERIAL primary key ,
                      firstName varchar(255),
                      lastName varchar(255),
                      mail varchar(255),
                      password varchar(255),
                      role varchar(255),
                      status varchar(55)
);