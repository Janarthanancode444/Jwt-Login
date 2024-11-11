package com.example.schoolmanagement.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class RoleRequestDTO {

    private String name;
    private String department;
    private String userId;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;
}