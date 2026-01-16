package com.codewithanny.appointmentservice.service;

import com.codewithanny.appointmentservice.dto.AppointmentResponseDto;
import com.codewithanny.appointmentservice.entity.CachedStudent;
import com.codewithanny.appointmentservice.repository.AppointmentRepository;
import com.codewithanny.appointmentservice.repository.CachedStudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final CachedStudentRepository cachedStudentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,  CachedStudentRepository cachedStudentRepository) {
        this.appointmentRepository = appointmentRepository;
        this.cachedStudentRepository = cachedStudentRepository;
    }

    public List<AppointmentResponseDto> getAppointmentsByDateRange(LocalDateTime from, LocalDateTime to) {
        return appointmentRepository.findByStartTimeBetween(from, to).stream()
                .map(appointment -> {

                    String name = cachedStudentRepository
                            .findById(appointment.getStudentId())
                            .map(CachedStudent::getFullName)
                            .orElse("Unknown");

                    AppointmentResponseDto appointmentResponseDto
                            = new AppointmentResponseDto();

                    appointmentResponseDto.setId(appointment.getId());
                    appointmentResponseDto.setStudentId(appointment.getStudentId());
                    appointmentResponseDto.setStartTime(appointment.getStartTime());
                    appointmentResponseDto.setEndTime(appointment.getEndTime());
                    appointmentResponseDto.setReason(appointment.getReason());
                    appointmentResponseDto.setVersion(appointment.getVersion());
                    appointmentResponseDto.setStudentName(name);

                    return appointmentResponseDto;
                }).toList();
    }
}
