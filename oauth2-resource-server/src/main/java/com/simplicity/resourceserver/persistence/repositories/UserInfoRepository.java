package com.simplicity.resourceserver.persistence.repositories;

import com.simplicity.resourceserver.persistence.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long>  {
}
