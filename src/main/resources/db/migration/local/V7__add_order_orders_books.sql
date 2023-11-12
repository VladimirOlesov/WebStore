INSERT INTO orders (user_id, order_date, status)
VALUES (1, null, 'IN_CART');

INSERT INTO orders_books (order_id, book_id)
SELECT (SELECT order_id
        FROM orders
        WHERE status = 'IN_CART' AND user_id = 1), 1;