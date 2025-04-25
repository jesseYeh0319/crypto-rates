package com.example.cryptorates.dto.external.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyDetail {
    private String code;
    private String symbol;
    private String rate;
    private String description;
    private BigDecimal rate_float;
}
