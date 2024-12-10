package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.entity.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StandardRepository extends JpaRepository<Standard, String> {
    List<Standard> findByName(String name);

   @Query(value = "select s from Standard s where s.name<:range")
    List<Standard> findByStandardLessThan(final String range);

    @Query(value = "select s from Standard s where s.name>:range")
    List<Standard> findByStandardGreaterThan(final String range);
}
