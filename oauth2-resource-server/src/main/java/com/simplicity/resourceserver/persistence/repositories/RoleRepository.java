package com.simplicity.resourceserver.persistence.repositories;

import com.simplicity.resourceserver.persistence.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
