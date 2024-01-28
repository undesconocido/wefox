package com.wefox.techtest.domain.service;

import com.wefox.techtest.adapter.outbound.LogErrorAdapter;
import com.wefox.techtest.domain.model.Error;
import com.wefox.techtest.domain.model.ErrorType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class LogErrorService {
    private final LogErrorAdapter logErrorAdapter;

    public void log(final String paymentId, final ErrorType errorType, final String description) {
        log.debug("Logging error {} ", paymentId);
        logErrorAdapter.execute(
                Error.builder()
                        .paymentId(paymentId)
                        .errorType(errorType)
                        .description(description)
                        .build()
        );
    }
}
