-- based on MySQL 8.0
CREATE INDEX index_orders_user_id_id
    ON idus.orders (user_id, id DESC);

CREATE UNIQUE INDEX index_orders_order_number
    ON idus.orders (order_number);
