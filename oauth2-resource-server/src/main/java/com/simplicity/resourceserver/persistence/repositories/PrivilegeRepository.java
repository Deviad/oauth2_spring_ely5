package com.simplicity.resourceserver.persistence.repositories;

import com.simplicity.resourceserver.persistence.domain.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

}
