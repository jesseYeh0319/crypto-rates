package com.example.cryptorates.dto.external.response;


import lombok.Data;

import java.util.Map;

@Data
public class CurrencyRateResponse {
    private Time time;
    private String disclaimer;
    private String chartName;
    private Map<String, CurrencyDetail> bpi;
}