package dev.vorstu.repositories;

import dev.vorstu.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.stream.Stream;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByUserId(Long userId);

}
