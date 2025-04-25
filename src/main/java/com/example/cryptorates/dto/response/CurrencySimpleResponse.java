package com.example.cryptorates.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrencySimpleResponse {
    private String name;
    private BigDecimal rate;
}