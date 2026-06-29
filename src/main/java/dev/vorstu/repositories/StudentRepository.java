package dev.vorstu.repositories;

import dev.vorstu.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUserId(Long userId);
    Stream<Student> findAllByGroupId(Long groupId);

}
