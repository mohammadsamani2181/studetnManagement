package com.studentManagement.repository;

import com.studentManagement.model.Principal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PrincipalRepository extends JpaRepository<Principal, Long> {
    @Query(value = "SELECT * FROM studentM_principal LIMIT 1", nativeQuery = true)
    Principal getPrincipal();
}
