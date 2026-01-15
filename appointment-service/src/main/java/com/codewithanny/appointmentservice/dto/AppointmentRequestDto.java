package com.codewithanny.appointmentservice.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public class AppointmentRequestDto {

    @NotNull(message = "studentId is required")
    private UUID studentId;

    @NotNull(message = "startTime is required")
    @Future(message = "startTime must be in the future")
    private LocalDateTime startTime;

    @NotNull(message = "endTime is required")
    @Future(message = "endTime must be in the future")
    private LocalDateTime endTime;

    @NotBlank(message = "reason is required")
    @Size(max = 255, message = "reason must be 255 characters or less")
    private String reason;

    // 👇 Optional, if not sent, defaults to 0
    private Long version = 0L;

    public AppointmentRequestDto() {}

    public AppointmentRequestDto(UUID studentId, LocalDateTime startTime, LocalDateTime endTime, String reason, Instant updatedAt) {
        this.studentId = studentId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
    }

    public UUID getstudentId() {
        return studentId;
    }

    public void setstudentId(UUID studentId) {
        this.studentId = studentId;
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
