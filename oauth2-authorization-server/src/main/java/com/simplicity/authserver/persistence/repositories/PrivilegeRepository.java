package com.simplicity.authserver.persistence.repositories;

import com.simplicity.authserver.persistence.domain.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

}
