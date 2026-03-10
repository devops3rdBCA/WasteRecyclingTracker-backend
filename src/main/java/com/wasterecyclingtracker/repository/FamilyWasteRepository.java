package com.wasterecyclingtracker.repository;

import com.wasterecyclingtracker.entity.FamilyWaste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyWasteRepository extends JpaRepository<FamilyWaste, Long> {
    List<FamilyWaste> findByFamilyName(String familyName);
    List<FamilyWaste> findByStatus(FamilyWaste.WasteStatus status);
}
