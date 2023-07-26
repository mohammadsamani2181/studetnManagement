package com.studentManagement.repository;

import com.studentManagement.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
    @Query(value = "SELECT * FROM studentM_school LIMIT 1", nativeQuery = true)
    public School getSchool();
}
