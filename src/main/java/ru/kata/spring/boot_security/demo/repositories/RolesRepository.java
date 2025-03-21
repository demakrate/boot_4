package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.db.models.Role;

import java.util.Optional;


public interface RolesRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String name);
}
