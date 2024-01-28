package com.wefox.techtest.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Error {
    @JsonProperty("payment_id")
    String paymentId;
    @JsonProperty("error_type")
    ErrorType errorType;
    @JsonProperty("error_description")
    String description;
}
