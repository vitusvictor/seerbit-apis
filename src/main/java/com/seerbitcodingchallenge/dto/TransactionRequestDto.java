package com.seerbitcodingchallenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDto {
    @JsonProperty("amount")
    private String amount;

    @JsonProperty("timestamp")
    private String timestamp;
}
