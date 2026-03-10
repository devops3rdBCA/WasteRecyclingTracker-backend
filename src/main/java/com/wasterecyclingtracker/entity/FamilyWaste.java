package com.wasterecyclingtracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "family_waste")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamilyWaste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String familyName;

    @Column(nullable = false)
    private String wasteType;

    @Column(nullable = false)
    private Double quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WasteStatus status = WasteStatus.PENDING;

    @Column(name = "created_at", updatable = false)
    private Long createdAt = System.currentTimeMillis();

    public enum WasteStatus {
        PENDING,
        PROCESSING,
        RECYCLED
    }
}
