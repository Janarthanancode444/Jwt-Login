package com.example.schoolmanagement.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class SchoolRequestDTO {
    private String name;
    private String address;
    private String phone;
    private String email;
    private String userId;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;
}
