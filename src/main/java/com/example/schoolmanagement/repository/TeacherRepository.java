package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {
    List<Teacher> findByEmail(String email);

    List<Teacher> findByPhone(String phone);
}
