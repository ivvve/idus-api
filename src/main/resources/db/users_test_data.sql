-- based on MySQL 8.0
# -- If you want to reset data
# DELETE FROM users WHERE id < 100000
# ALTER TABLE users AUTO_INCREMENT = 1;

DROP PROCEDURE IF EXISTS insert_users_test_data;

CREATE PROCEDURE insert_users_test_data()
BEGIN
    DECLARE i INT DEFAULT 1;
    WHILE i <= 10000
        DO
            INSERT INTO idus.users(name,
                                   nickname,
                                   encoded_password,
                                   phone_number,
                                   email,
                                   gender,
                                   created_at,
                                   updated_at)
            VALUES (concat('이름', i),
                    concat('nickname', i),
                    concat('Passw@rd', i),
                    concat('0101111', i),
                    concat(concat('email', i), '@gmail.com'),
                    case i % 3
                        when 0 then 'MALE'
                        when 1 then 'FEMALE'
                        when 2 then null end,
                    now(),
                    now());
            SET i = i + 1;
        END WHILE;
END;

CALL insert_users_test_data();

DROP PROCEDURE IF EXISTS insert_users_test_data;
