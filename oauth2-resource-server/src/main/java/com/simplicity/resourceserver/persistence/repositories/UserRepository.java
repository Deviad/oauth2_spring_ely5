package com.simplicity.resourceserver.persistence.repositories;

import com.simplicity.resourceserver.persistence.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u")
    List<User> findAllUsersWithLimit(Pageable pageable);
    @Query("SELECT u FROM User u WHERE username = :username")
    User findUserByUsername(@Param("username") String username);
//    default List<User>findAllWithLimit10() {
//        return findAllUsersWithLimit(new PageRequest(0, 10));
//    }
}
