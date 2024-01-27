package com.wefox.techtest.adapter.inbound;

import com.wefox.techtest.app.PaymentService;
import com.wefox.techtest.model.Payment;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

@Component
@AllArgsConstructor
@Log
public class KafkaConsumerAdapter {
    private final PaymentService paymentService;

    @KafkaListener(topics = {"online", "offline"}, groupId = "${spring.kafka.consumer.group-id}")
    public void consumePayments(final ConsumerRecords<String, Payment> messages) {
        messages.forEach(message -> {
            String key = message.key();
            Payment payment = message.value();
            log.info(format("Received payment with key {0} and value {1}", key, payment));
        });
    }
}

