package com.example.schoolmanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "mark")
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    private Subject subject;
    @ManyToOne
    private User user;
    @ManyToOne
    private Student student;
    @Column(name = "created_at")
    @CurrentTimestamp
    private Instant createdAt;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "mark")
    private Integer mark;
}
