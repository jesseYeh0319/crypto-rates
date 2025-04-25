package com.example.cryptorates.service;

import com.example.cryptorates.dto.external.response.CurrencyDetail;
import com.example.cryptorates.dto.external.response.CurrencyRateResponse;
import com.example.cryptorates.dto.response.CoinDeskTransformedResponse;
import com.example.cryptorates.dto.response.CurrencySimpleResponse;
import com.example.cryptorates.entity.Currency;
import com.example.cryptorates.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CurrencyViewServiceImpl implements CurrencyViewService {

    @Value("${api.url}")
    private String url;

    private final CurrencyRepository repository;
    private final RestTemplate restTemplate;

    public CurrencyViewServiceImpl(CurrencyRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    @Override
    public CoinDeskTransformedResponse getConvertedView() {
        // 取得幣別中英文對照表
        Map<String, String> currencyMap = this.getCurrencyNameMap();

        // 呼叫 CoinDesk API
        CurrencyRateResponse currencyRateResponse = restTemplate.getForObject(url, CurrencyRateResponse.class);

        // 將幣別資訊轉換成簡易格式
        Map<String, CurrencySimpleResponse> currencySimpleMap = this.convertToSimpleMap(currencyRateResponse, currencyMap);

        // Step 4: 包裝回傳結果
        CoinDeskTransformedResponse response = new CoinDeskTransformedResponse();
        response.setCurrencies(currencySimpleMap);
        response.setUpdateTime(LocalDateTime.now());
        return response;
    }

    // 中英文名稱對照 Map
    private Map<String, String> getCurrencyNameMap() {
        return repository.findAll().stream().filter(c -> c.getCode() != null && c.getZhName() != null).collect(Collectors.toMap(Currency::getCode, Currency::getZhName));
    }

    // 轉換邏輯
    private Map<String, CurrencySimpleResponse> convertToSimpleMap(CurrencyRateResponse response, Map<String, String> currencyMap) {
        return Objects.requireNonNull(response).getBpi().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> {
            CurrencyDetail detail = entry.getValue();
            String code = detail.getCode();
            String name = currencyMap.get(code);
            BigDecimal rate = detail.getRate_float();
            return new CurrencySimpleResponse(name, rate);
        }));
    }
}