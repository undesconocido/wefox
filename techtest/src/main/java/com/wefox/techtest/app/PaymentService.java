package com.wefox.techtest.app;

import com.wefox.techtest.model.Payment;

public interface PaymentService {

    void processOnline(Payment payment);
    void processOffline(Payment payment);
}
