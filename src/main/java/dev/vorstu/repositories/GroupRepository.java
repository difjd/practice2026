package dev.vorstu.repositories;

import dev.vorstu.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GroupRepository extends JpaRepository<Group, Long> {
}
