package com.codewithanny.appointmentservice.kafka;

import com.codewithanny.appointmentservice.entity.Appointment;
import com.codewithanny.appointmentservice.entity.CachedStudent;
import com.codewithanny.appointmentservice.repository.CachedStudentRepository;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import student.events.StudentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.UUID;

@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);
    private final CachedStudentRepository cachedstudentRepository;

    public KafkaConsumer(CachedStudentRepository cachedstudentRepository) {
        this.cachedstudentRepository = cachedstudentRepository;
    }

    @KafkaListener(
            topics={"student.created", "student.updated"},
            groupId = "appointment-service")
    public void consumeEvent(byte[] event) {
        try {
            StudentEvent studentEvent = StudentEvent.parseFrom(event);

            log.info("Received Student Event: {}", studentEvent.toString());

            CachedStudent cachedStudent = new CachedStudent();
            cachedStudent.setId(UUID.fromString(studentEvent.getStudentId()));
            cachedStudent.setFullName(studentEvent.getName());
            cachedStudent.setEmail(studentEvent.getEmail());
            cachedStudent.setUpdatedDate(Instant.now());

            cachedstudentRepository.save(cachedStudent);
        } catch (InvalidProtocolBufferException e) {
            log.error("Error deserializing Student Event: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Error consuming Student Event: {}", e.getMessage());
        }
    }
}
