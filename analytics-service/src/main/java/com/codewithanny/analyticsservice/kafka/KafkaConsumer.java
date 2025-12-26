package com.codewithanny.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import student.events.StudentEvent;

@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics="student", groupId = "analytics-service")
    public void consumeEvent(byte[] event) {
        try {
            StudentEvent studentEvent = StudentEvent.parseFrom(event);
            // ... perform any business related to analytics here

            log.info("Received Student Event: [StudentId={}, StudentName={}, " + "StudentEmail={} ]", studentEvent.getStudentId(), studentEvent.getName(), studentEvent.getEmail());
        } catch (InvalidProtocolBufferException e) {
            log.error("Error deserializing event {}", e.getMessage());
        }
    }
}
