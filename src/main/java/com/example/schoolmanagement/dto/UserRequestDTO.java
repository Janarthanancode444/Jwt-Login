package com.example.schoolmanagement.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class UserRequestDTO {
    private String name;
    private String phone;
    private String email;
    private String password;
    private String roles;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;
}
