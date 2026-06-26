package dev.vorstu.repositories;

import dev.vorstu.dto.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student, Long> {
    Optional<Student> findFirstByGroup(String group);
}
