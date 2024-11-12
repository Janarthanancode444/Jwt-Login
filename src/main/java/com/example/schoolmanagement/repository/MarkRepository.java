package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.entity.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark, String> {

    List<Mark> findByMarkLessThan(Integer mark);

    @Query("SELECT m FROM Mark m ORDER BY m.mark ASC")
    List<Mark> findAllOrderByMarkAsc();

    @Query("SELECT m FROM Mark m ORDER BY m.mark desc")
    List<Mark> findAllOrderByMarkDesc();
}
