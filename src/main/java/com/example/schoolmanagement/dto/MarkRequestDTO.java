package com.example.schoolmanagement.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class MarkRequestDTO {
    private String userId;
    private String studentId;
    private String subjectId;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;
    private Integer mark;
}
