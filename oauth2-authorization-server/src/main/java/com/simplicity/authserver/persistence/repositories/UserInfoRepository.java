package com.simplicity.authserver.persistence.repositories;

import com.simplicity.authserver.persistence.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long>  {
}
