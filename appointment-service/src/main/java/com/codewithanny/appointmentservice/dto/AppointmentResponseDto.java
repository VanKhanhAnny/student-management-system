package com.codewithanny.appointmentservice.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class AppointmentResponseDto {

    private UUID id;
    private UUID studentId;
    private String studentName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String reason;
    private Long version; // ➡️ Added version for optimistic locking


    public AppointmentResponseDto() {}

    public AppointmentResponseDto(UUID id, UUID studentId, String studentName,
                                  LocalDateTime startTime, LocalDateTime endTime, String reason, Long version) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getstudentId() {
        return studentId;
    }

    public void setstudentId(UUID studentId) {
        this.studentId = studentId;
    }

    public String getstudentName() {
        return studentName;
    }

    public void setstudentName(String studentName) {
        this.studentName = studentName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
