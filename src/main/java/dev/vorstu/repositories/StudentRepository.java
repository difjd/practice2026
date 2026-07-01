package dev.vorstu.repositories;

import dev.vorstu.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUserId(Long userId);
    Stream<Student> findAllByGroupId(Long groupId);
    Stream<Student> findAllByGroupIdIn(List<Long> groupIds);

    //все студенты той же группы
    @Query(value = """
        SELECT s.*
        FROM students s
        JOIN students current_student ON s.group_id = current_student.group_id
        WHERE current_student.user_id = :userId
        """, nativeQuery = true)
    Stream<Student> findGroupmatesByStudentUserId(@Param("userId") Long userId);

    //преподаватель смотрит студентов своих групп
    @Query(value = """
        SELECT s.*
        FROM students s
        JOIN teacher_groups tg ON s.group_id = tg.group_id
        JOIN teachers t ON tg.teacher_id = t.id
        WHERE t.user_id = :userId
        """, nativeQuery = true)
    Stream<Student> findStudentsByTeacherUserId(@Param("userId") Long userId);

    //может ли преподаватель редактировать студента
    @Query(value = """
        SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END
        FROM students s
        JOIN teacher_groups tg ON s.group_id = tg.group_id
        JOIN teachers t ON tg.teacher_id = t.id
        WHERE s.id = :studentId
          AND t.user_id = :userId
        """, nativeQuery = true)
    boolean existsStudentInTeacherGroups(@Param("studentId") Long studentId, @Param("userId") Long userId);

}
