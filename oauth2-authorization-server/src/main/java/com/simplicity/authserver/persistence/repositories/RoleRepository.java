package com.simplicity.authserver.persistence.repositories;

import com.simplicity.authserver.persistence.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
