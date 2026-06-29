--liquibase formatted sql

--changeset ariana:001
CREATE TABLE students(
    id BIGSERIAL PRIMARY KEY,
    fio VARCHAR(255),
    group_of_students VARCHAR(255),
    phone_number VARCHAR(255)
);

--rollback DROP TABLE students;