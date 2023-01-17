DROP TABLE  IF EXISTS favorite_products;
create TABLE favorite_products(
                        id SERIAL PRIMARY KEY,
                        user_id BIGINT
);

DROP TABLE IF EXISTS users_favorites;
CREATE TABLE  users_favorites(
                         id SERIAL PRIMARY KEY ,
                         user_id BIGINT,
                         favorite_id BIGINT
);

DROP TABLE IF EXISTS favorites_products;
CREATE TABLE  favorites_products(
                                 id SERIAL PRIMARY KEY ,
                                 favorite_id BIGINT,
                                 product_id BIGINT
);

DROP TABLE IF EXISTS products_favorites;
CREATE TABLE  products_favorites(
                                    id SERIAL PRIMARY KEY ,
                                    user_id BIGINT,
                                    product_id BIGINT
);