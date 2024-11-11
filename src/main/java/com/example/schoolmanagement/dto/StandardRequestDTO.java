package com.example.schoolmanagement.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class StandardRequestDTO {
    private String name;
    private int totalStudent;
    private String userId;
    private String schoolId;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;
}
