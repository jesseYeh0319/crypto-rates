package com.example.cryptorates.service;

import com.example.cryptorates.dto.request.CurrencyRateRequest;
import com.example.cryptorates.dto.response.CurrencyResponse;

import java.util.List;

public interface CurrencyService {
    List<CurrencyResponse> findAll();

    CurrencyResponse findOne(String id);

    CurrencyResponse create(CurrencyRateRequest request);

    CurrencyResponse update(CurrencyRateRequest updated);

    void delete(String id);
}
