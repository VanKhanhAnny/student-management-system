package com.codewithanny.studentservice.kafka;

import billing.events.BillingAccountEvent;
import com.codewithanny.studentservice.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import student.events.StudentEvent;

@Service
public class KafkaProducer {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendStudentCreatedEvent(Student student) {
        StudentEvent event = StudentEvent.newBuilder()
                .setStudentId(student.getId().toString())
                .setName(student.getName())
                .setEmail(student.getEmail())
                .build();

        try {
            kafkaTemplate.send("student.created", event.toByteArray());
        } catch (Exception e) {
            log.error("Error sending StudentCreated event: {}", event);
        }
    }

    public void sendBillingAccountEvent(String studentId, String name, String email) {
        BillingAccountEvent event = BillingAccountEvent.newBuilder()
                .setStudentId(studentId)
                .setName(name)
                .setEmail(email)
                .setEventType("BILLING_ACCOUNT_CREATE_REQUESTED")
                .build();

        try {
            kafkaTemplate.send("billing-account", event.toByteArray());
        } catch (Exception e) {
            log.error("Error sending BillingAccountCreated event: {}", event);
        }
    }
}
