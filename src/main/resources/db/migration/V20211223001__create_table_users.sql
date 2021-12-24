-- based on MySQL 8.0
CREATE TABLE idus.users
(
    id               BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    email            VARCHAR(100) NOT NULL,
    encoded_password VARCHAR(100) NOT NULL,
    name             VARCHAR(50)  NOT NULL,
    nickname         VARCHAR(50)  NOT NULL,
    phone_number     VARCHAR(20)  NOT NULL,
    gender           VARCHAR(50)  NULL,
    created_at       DATETIME     NOT NULL,
    updated_at       DATETIME     NOT NULL
);
