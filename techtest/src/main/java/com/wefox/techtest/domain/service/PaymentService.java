package com.wefox.techtest.domain.service;

import com.wefox.techtest.domain.model.Payment;

public interface PaymentService {

    void process(Payment payment);
}
