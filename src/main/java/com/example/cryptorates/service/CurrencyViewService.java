package com.example.cryptorates.service;

import com.example.cryptorates.dto.response.CoinDeskTransformedResponse;

public interface CurrencyViewService {
    CoinDeskTransformedResponse getConvertedView();
}