DROP TABLE  IF EXISTS client;
create TABLE client (
                        id SERIAL primary key ,
                        name varchar(255),
                        email varchar(255),
                        role varchar(255),
                        status varchar(55),
                        lastname varchar(80),
                        bucket_id BIGINT,
                        address varchar(255)
);
INSERT INTO  client (id,name,email,role,status,lastname,bucket_id,address)
VALUES ( 2,
         'Admin',
         'Mail@mail',
         'ADMIN',
         'ACTIVE',
         'Adminov',
         1,
         'Moscow'
       );
