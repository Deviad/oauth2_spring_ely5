package com.example.springdemo.persistence.repositories;

import com.example.springdemo.persistence.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long>  {
}
