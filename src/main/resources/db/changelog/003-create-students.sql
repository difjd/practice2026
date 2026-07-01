--liquibase formatted sql

--changeset ariana:003-create-students
CREATE TABLE students (
    id BIGSERIAL PRIMARY KEY,
    fio VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    group_id BIGINT,
    user_id BIGINT UNIQUE,

    CONSTRAINT fk_students_group
        FOREIGN KEY (group_id) REFERENCES groups(id),

    CONSTRAINT fk_students_user
        FOREIGN KEY (user_id) REFERENCES users(id)
);

--rollback DROP TABLE students;