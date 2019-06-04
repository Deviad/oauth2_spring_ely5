package com.example.springdemo.persistence.repositories;

import com.example.springdemo.persistence.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
