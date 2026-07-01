--liquibase formatted sql

--changeset ariana:005-create-admins
CREATE TABLE admins (
    id BIGSERIAL PRIMARY KEY,
    fio VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    user_id BIGINT UNIQUE,

    CONSTRAINT fk_admins_user
        FOREIGN KEY (user_id) REFERENCES users(id)
);

--rollback DROP TABLE admins;