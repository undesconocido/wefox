package com.wefox.techtest.adapter.inbound;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.wefox.techtest.domain.model.Payment;
import com.wefox.techtest.domain.service.OfflinePaymentService;
import com.wefox.techtest.domain.service.OnlinePaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class KafkaConsumerAdapterTest {
    private static final String DUMMY_MESSAGE = "dummyMessage";
    private static final Payment PAYMENT = Payment.builder().build();
    private JsonMapper mapper;
    private OnlinePaymentService onlinePaymentService;
    private OfflinePaymentService offlinePaymentService;
    private KafkaConsumerAdapter kafkaConsumerAdapter;

    @BeforeEach
    void setUp() {
        mapper = Mockito.mock(JsonMapper.class);
        onlinePaymentService = Mockito.mock(OnlinePaymentService.class);
        offlinePaymentService = Mockito.mock(OfflinePaymentService.class);
        kafkaConsumerAdapter = new KafkaConsumerAdapter(mapper, onlinePaymentService, offlinePaymentService);
    }

    @Test
    void consumeOnlineTopic() throws JsonProcessingException {
        when(mapper.readValue(DUMMY_MESSAGE, Payment.class)).thenReturn(PAYMENT);

        kafkaConsumerAdapter.consumeOnlineTopic(DUMMY_MESSAGE);

        verify(onlinePaymentService).process(PAYMENT);
    }

    @Test
    void consumeOfflineTopic() throws JsonProcessingException {
        when(mapper.readValue(DUMMY_MESSAGE, Payment.class)).thenReturn(PAYMENT);

        kafkaConsumerAdapter.consumeOfflineTopic(DUMMY_MESSAGE);

        verify(offlinePaymentService).process(PAYMENT);
    }
}