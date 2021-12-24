-- based on MySQL 8.0
CREATE UNIQUE INDEX index_users_email
    ON idus.users (email);

CREATE INDEX index_users_name
    ON idus.users (name);
