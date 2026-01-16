package com.codewithanny.appointmentservice.repository;

import com.codewithanny.appointmentservice.entity.CachedStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CachedStudentRepository extends JpaRepository<CachedStudent, UUID> {
}
