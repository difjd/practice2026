--liquibase formatted sql

--changeset ariana:007-insert-test-data
INSERT INTO groups (id, name)
VALUES
    (1, 'VM'),
    (2, 'AM');

INSERT INTO users (id, email, password_hash, role)
VALUES
    (1, 'student@test.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoOhiE0fR3UF3A0NzS9uXj.T4HGfGz9VJ2', 'STUDENT'),
    (2, 'teacher@test.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoOhiE0fR3UF3A0NzS9uXj.T4HGfGz9VJ2', 'TEACHER'),
    (3, 'admin@test.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoOhiE0fR3UF3A0NzS9uXj.T4HGfGz9VJ2', 'ADMIN');

INSERT INTO students (id, fio, phone_number, group_id, user_id)
VALUES
    (1, 'Student Test', '+70000000001', 1, 1);

INSERT INTO teachers (id, fio, phone_number, user_id)
VALUES
    (1, 'Teacher Test', '+70000000002', 2);

INSERT INTO admins (id, fio, phone_number, user_id)
VALUES
    (1, 'Admin Test', '+70000000003', 3);

INSERT INTO teacher_groups (teacher_id, group_id)
VALUES
    (1, 1);

--rollback DELETE FROM teacher_groups WHERE teacher_id = 1 AND group_id = 1;
--rollback DELETE FROM admins WHERE id = 1;
--rollback DELETE FROM teachers WHERE id = 1;
--rollback DELETE FROM students WHERE id = 1;
--rollback DELETE FROM users WHERE id IN (1, 2, 3);
--rollback DELETE FROM groups WHERE id IN (1, 2);