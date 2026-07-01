--liquibase formatted sql

--changeset ariana:004-create-teachers
CREATE TABLE teachers (
    id BIGSERIAL PRIMARY KEY,
    fio VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    user_id BIGINT UNIQUE,

    CONSTRAINT fk_teachers_user
        FOREIGN KEY (user_id) REFERENCES users(id)
);

--rollback DROP TABLE teachers;