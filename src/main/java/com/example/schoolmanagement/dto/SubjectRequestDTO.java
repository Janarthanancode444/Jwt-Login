package com.example.schoolmanagement.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class SubjectRequestDTO {
    private String name;
    private String userId;
    private String standardId;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;
}
