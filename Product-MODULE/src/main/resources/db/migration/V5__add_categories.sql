INSERT INTO  categories (id, title)
VALUES (1, 'dairy'),
       (2, 'Bakery'),
       (3, 'Alcohol'),
       (4, 'Vegetable');

INSERT INTO  products_categories (id, product_id, category_id)
VALUES (1, 1, 1),
       (2, 2, 3),
       (3, 3, 1),
       (4, 4, 4),
       (5, 5, 2);