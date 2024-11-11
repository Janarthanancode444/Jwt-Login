package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.entity.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<School, String> {
    @Query("SELECT s FROM School s " +
            "WHERE (:search IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%'))) OR " +
            "(:search IS NULL OR LOWER(s.address) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<School> searchSchool(@Param("search") String search, Pageable pageable);

    List<School> findByEmail(String email);

    List<School> findByPhone(String phone);
}
