-- based on MySQL 8.0
CREATE TABLE idus.orders
(
    id           BIGINT UNSIGNED KEY AUTO_INCREMENT,
    user_id      BIGINT       NOT NULL,
    order_number VARCHAR(50)  NOT NULL,
    product_name VARCHAR(200) NOT NULL,
    ordered_at   DATETIME     NOT NULL,
    created_at   DATETIME     NOT NULL,
    updated_at   DATETIME     NOT NULL
);
