DROP TABLE  IF EXISTS buckets;
create TABLE buckets(
                        id SERIAL PRIMARY KEY,
                        user_mail varchar(100)
);


DROP TABLE  IF EXISTS products;
create TABLE products(
                         id SERIAL PRIMARY KEY,
                         title varchar(255),
                         price DECIMAL,
                         count_in_stock BIGINT


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



