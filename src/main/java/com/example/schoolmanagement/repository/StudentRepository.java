package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.entity.School;
import com.example.schoolmanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,String> {
    List<Student> findByEmail(String email);
    List<Student> findByPhone(String phone);
}
