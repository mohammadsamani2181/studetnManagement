package com.studentManagement.repository;

import com.studentManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM studentM_user LIMIT 1", nativeQuery = true)
    Optional<User> getAdmin();

    Optional<User> findBySsoId(Long ssoId);
}
