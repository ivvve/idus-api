-- based on MySQL 8.0
# -- If you want to reset data
# DELETE FROM orders WHERE id < 100000
# ALTER TABLE orders AUTO_INCREMENT = 1;

DROP PROCEDURE IF EXISTS insert_orders_test_data;

CREATE PROCEDURE insert_orders_test_data()
BEGIN
    DECLARE i INT DEFAULT 0;
    WHILE i < 10000
        DO
            INSERT INTO idus.orders(user_id,
                                    order_number,
                                    product_name,
                                    ordered_at,
                                    created_at,
                                    updated_at)
            VALUES (i % 100 + 1,
                    lpad(i, 12, '0'),
                    concat('제품', i),
                    now(),
                    now(),
                    now());
            SET i = i + 1;
        END WHILE;
END;

CALL insert_orders_test_data();

DROP PROCEDURE IF EXISTS insert_orders_test_data;
