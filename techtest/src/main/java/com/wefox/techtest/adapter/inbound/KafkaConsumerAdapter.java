package com.wefox.techtest.adapter.inbound;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.wefox.techtest.domain.model.Payment;
import com.wefox.techtest.domain.service.OfflinePaymentService;
import com.wefox.techtest.domain.service.OnlinePaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaConsumerAdapter {
    private final JsonMapper mapper;
    private final OnlinePaymentService onlinePaymentService;
    private final OfflinePaymentService offlinePaymentService;

    @KafkaListener(topics = "online", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOnlineTopic(final String message) throws JsonProcessingException {
        log.debug("Received online payment {}", message);
        try {
            onlinePaymentService.process(mapper.readValue(message, Payment.class));
        } catch (final Exception e) {
            log.debug("Error processing online payment {}, error {}", message, e.getMessage(), e);
            // Create a fallback topic with retry counter to avoid infinite loop for transactions that are not processed
        }
    }

    @KafkaListener(topics = "offline", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOfflineTopic(final String message) throws JsonProcessingException {
        log.debug("Received offline payment {}", message);
        try {
            offlinePaymentService.process(mapper.readValue(message, Payment.class));
        } catch (final Exception e) {
            log.debug("Error processing offline payment {}, error {}", message, e.getMessage(), e);
            // Create a fallback topic with retry counter to avoid infinite loop for transactions that are not processed
        }
    }
}

