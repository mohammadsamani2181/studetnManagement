package com.studentManagement.repository;

import com.studentManagement.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.w3c.dom.ls.LSInput;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
    @Query(value = "SELECT * FROM studentM_school LIMIT 1", nativeQuery = true)
    public School getTheSchool();
}
