--liquibase formatted sql

--changeset ariana:001
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

--changeset ariana:002
CREATE TABLE groups (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

--changeset ariana:003
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

--changeset ariana:004
CREATE TABLE teachers (
    id BIGSERIAL PRIMARY KEY,
    fio VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    user_id BIGINT UNIQUE,

    CONSTRAINT fk_teachers_user
        FOREIGN KEY (user_id) REFERENCES users(id)
);

--changeset ariana:005
CREATE TABLE admins (
    id BIGSERIAL PRIMARY KEY,
    fio VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    user_id BIGINT UNIQUE,

    CONSTRAINT fk_admins_user
        FOREIGN KEY (user_id) REFERENCES users(id)
);

--changeset ariana:006
CREATE TABLE teacher_groups (
    teacher_id BIGINT NOT NULL,
    group_id BIGINT NOT NULL,

    CONSTRAINT pk_teacher_groups
        PRIMARY KEY (teacher_id, group_id),

    CONSTRAINT fk_teacher_groups_teacher
        FOREIGN KEY (teacher_id) REFERENCES teachers(id),

    CONSTRAINT fk_teacher_groups_group
        FOREIGN KEY (group_id) REFERENCES groups(id)
);

--rollback DROP TABLE teacher_groups;
--rollback DROP TABLE admins;
--rollback DROP TABLE teachers;
--rollback DROP TABLE students;
--rollback DROP TABLE groups;
--rollback DROP TABLE users;