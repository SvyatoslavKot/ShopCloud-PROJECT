DROP TABLE  IF EXISTS buckets;
create TABLE buckets(
                        id SERIAL PRIMARY KEY,
                        user_id BIGINT
);

DROP TABLE  IF EXISTS users;
create TABLE users (
                       id SERIAL primary key ,
                       name varchar(255),
                       e_mail varchar(255),
                       password varchar(255),
                       archive boolean,
                       role varchar(255),
                       bucket_id BIGINT,
                       foreign key (bucket_id) references buckets (id)
);

DROP TABLE  IF EXISTS products;
create TABLE products(
                         id SERIAL PRIMARY KEY,
                         title varchar(255),
                         price DECIMAL

);

DROP TABLE IF EXISTS buckets_products;
CREATE TABLE  buckets_products(
                                  id SERIAL PRIMARY KEY ,
                                  bucket_id BIGINT,
                                  product_id BIGINT
);

DROP TABLE  IF EXISTS categories;
create TABLE categories(
                           id SERIAL PRIMARY KEY,
                           title varchar(255)
);

DROP TABLE IF EXISTS products_categories;
CREATE TABLE  products_categories(
                                     id SERIAL PRIMARY KEY ,
                                     product_id BIGINT,
                                     category_id BIGINT
);

DROP TABLE  IF EXISTS orders;
create TABLE orders(
                       id SERIAL PRIMARY KEY,
                       address varchar(255),
                       created timestamp,
                       update timestamp,
                       sum DECIMAL,
                       status varchar(255),
                       user_id BIGINT,
                       foreign key (user_id) references users(id)

);

DROP TABLE  IF EXISTS orders_details;
create TABLE orders_details(
                              id SERIAL PRIMARY KEY,
                              amount DECIMAL,
                              price DECIMAL,
                              order_id BIGINT,
                              foreign key (order_id) references orders (id),
                              product_id BIGINT,
                              foreign key (product_id) references products(id)

);


