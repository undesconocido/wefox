package com.wefox.techtest.adapter.inbound;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.wefox.techtest.app.PaymentService;
import com.wefox.techtest.model.Payment;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaConsumerAdapter {
    private final PaymentService paymentService;
    private final JsonMapper mapper;

    @KafkaListener(topics = "online", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOnlineTopic(final String record) throws JsonProcessingException {
        //log.info("Received payment {}", record);
        paymentService.processOnline(mapper.readValue(record, Payment.class));
    }

    @KafkaListener(topics = "offline", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOfflineTopic(final String record) throws JsonProcessingException {
        //log.info("Received record {}", record);
        paymentService.processOffline(mapper.readValue(record, Payment.class));
    }
}

