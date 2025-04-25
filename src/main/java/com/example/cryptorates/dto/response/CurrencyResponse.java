package com.example.cryptorates.dto.response;

import lombok.Data;

@Data
public class CurrencyResponse {
    private String code;
    private String symbol;
    private String enName;
    private String zhName;
}