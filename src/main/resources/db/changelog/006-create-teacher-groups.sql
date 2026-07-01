--liquibase formatted sql

--changeset ariana:006-create-teacher-groups
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