package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.entity.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public interface MarkRepository extends JpaRepository<Mark, String> {

    List<Mark> findByMarkLessThan(Integer mark);

    @Query("SELECT m FROM Mark m ORDER BY m.mark ASC")
    List<Mark> findAllOrderByMarkAsc();

    @Query("SELECT m FROM Mark m ORDER BY m.mark desc")
    List<Mark> findAllOrderByMarkDesc();

    @Query("SELECT m.id,m.mark from Mark m where  (:lesserThan=true AND m.mark<:mark)  OR (:lesserThan=false AND m.mark>:mark)")
    List<Object> search(@Param("mark") final Integer mark, @Param("lesserThan") final boolean lesserThan);
}
