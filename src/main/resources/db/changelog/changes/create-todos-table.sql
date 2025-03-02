--liquibase formatted sql
--changeset ssydorenko:create-todos-table
CREATE TABLE todos
(
    id          BIGINT   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(100),
    description VARCHAR(500),
    due_date    DATETIME,
    priority    VARCHAR(25),
    status      VARCHAR(25),
    created_at  DATETIME NOT NULL,
    updated_at  DATETIME NOT NULL,
    user_id     BIGINT   NOT NULL,
    is_deleted  BIT
) ENGINE=InnoDB;

--rollback DROP TABLE todos;