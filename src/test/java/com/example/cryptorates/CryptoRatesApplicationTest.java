package com.example.cryptorates;

import com.example.cryptorates.dto.request.CurrencyRateRequest;
import com.example.cryptorates.dto.response.CurrencyResponse;
import com.example.cryptorates.service.CurrencyService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class CryptoRatesApplicationTest {

    @Autowired
    private CurrencyService currencyRateService;


    @Order(1)
    @Test
    public void testInsert() {
        CurrencyRateRequest request = new CurrencyRateRequest();
        request.setCode("TWD");
        request.setSymbol("12&%");
        request.setEnName("test");
        request.setZhName("ZZZ");
        currencyRateService.create(request);
    }

    @Order(2)
    @Test
    public void testFindAll() {
        List<CurrencyResponse> all = currencyRateService.findAll();
    }

    @Order(3)
    @Test
    public void testFindOne() {
        currencyRateService.findOne("USD");
    }

    @Order(4)
    @Test
    public void testUpdate() {
        CurrencyRateRequest request = new CurrencyRateRequest();
        request.setCode("TWD");
        request.setSymbol("12&%");
        request.setEnName("test");
        request.setZhName("ZZZ");
        currencyRateService.update(request);
    }

    @Order(5)
    @Test
    public void testDelete() {
        currencyRateService.delete("TWD");
    }

}
