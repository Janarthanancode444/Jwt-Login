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
@Table(name = "section")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "section")
    private String section;
    @ManyToOne
    private Standard standard;
    @ManyToOne
    private Teacher teacher;
    @ManyToOne
    private User user;
    @Column(name = "created_at")
    @CurrentTimestamp
    private Instant createdAt;
    @Column(name = "created_by", nullable = true)
    private String createdBy;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;
    @Column(name = "updated_by", nullable = true)
    private String updatedBy;
}
