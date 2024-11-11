package com.example.schoolmanagement.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class TeacherRequestDTO {
    private String name;
    private String dateOfBirth;
    private String gender;
    private String subject;
    private String address;
    private String phone;
    private String email;
    private String userId;
    private String schoolId;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;
}
