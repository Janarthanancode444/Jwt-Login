package com.example.schoolmanagement.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class PermissionRequestDTO {
    private String teacherId;
    private String userId;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;
}
