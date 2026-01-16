package com.codewithanny.appointmentservice.kafka;

import com.codewithanny.appointmentservice.entity.Appointment;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import student.events.StudentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(
            topics={"student.created", "student.updated"},
            groupId = "appointment-service")
    public void consumeEvent(byte[] event) {
        try {
            StudentEvent studentEvent = StudentEvent.parseFrom(event);

            log.info("Received Student Event: {}", studentEvent.toString());
        } catch (InvalidProtocolBufferException e) {
            log.error("Error deserializing Student Event: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Error consuming Student Event: {}", e.getMessage());
        }
    }
}
