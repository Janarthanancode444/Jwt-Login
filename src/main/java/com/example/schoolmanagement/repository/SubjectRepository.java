package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping
public interface SubjectRepository extends JpaRepository<Subject, String> {
    List<Subject>findByName(String name);
}
