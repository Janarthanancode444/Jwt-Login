package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByName(String username);
}
