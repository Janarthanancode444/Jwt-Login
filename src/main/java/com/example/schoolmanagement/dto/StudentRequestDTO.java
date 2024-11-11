package com.example.schoolmanagement.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class StudentRequestDTO {
    private String name;
    private String dateOfBirth;
    private String gender;
    private String address;
    private String email;
    private String fathersName;
    private String mothersName;
    private String phone;
    private String teacherId;
    private String userId;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;
}
