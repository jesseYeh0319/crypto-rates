package com.example.cryptorates;

import com.example.cryptorates.dto.request.CurrencyRateRequest;
import com.example.cryptorates.dto.response.CurrencyResponse;
import com.example.cryptorates.entity.Currency;
import com.example.cryptorates.repository.CurrencyRepository;
import com.example.cryptorates.service.CurrencyServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CurrencyServiceUnitTest {

    @Mock
    private CurrencyRepository repository;

    @InjectMocks
    private CurrencyServiceImpl service;

    private Currency testCurrency;

    @BeforeEach
    void setUp() {
        testCurrency = new Currency();
        testCurrency.setCode("TWD");
        testCurrency.setSymbol("12&%");
        testCurrency.setEnName("test");
        testCurrency.setZhName("ZZZ");
    }

    @Test
    @Order(1)
    void testCreate() {
        CurrencyRateRequest request = new CurrencyRateRequest();
        request.setCode("TWD");
        request.setSymbol("12&%");
        request.setEnName("test");
        request.setZhName("ZZZ");

        when(repository.existsById("TWD")).thenReturn(false);
        when(repository.save(any(Currency.class))).thenReturn(testCurrency);

        CurrencyResponse result = service.create(request);

        assertEquals("TWD", result.getCode());
        assertEquals("test", result.getEnName());
    }

    @Test
    @Order(2)
    void testFindAll() {
        when(repository.findAll()).thenReturn(Collections.singletonList(testCurrency));

        List<CurrencyResponse> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("TWD", result.get(0).getCode());
    }

    @Test
    @Order(3)
    void testFindOne() {
        when(repository.findById("TWD")).thenReturn(Optional.of(testCurrency));

        CurrencyResponse result = service.findOne("TWD");

        assertEquals("TWD", result.getCode());
        assertEquals("ZZZ", result.getZhName());
    }

    @Test
    @Order(4)
    void testUpdate() {
        CurrencyRateRequest request = new CurrencyRateRequest();
        request.setCode("TWD");
        request.setSymbol("%%");
        request.setEnName("updated");
        request.setZhName("更新");

        when(repository.findById("TWD")).thenReturn(Optional.of(testCurrency));
        when(repository.save(any(Currency.class))).thenReturn(testCurrency);

        CurrencyResponse updated = service.update(request);

        assertEquals("TWD", updated.getCode());
    }

    @Test
    @Order(5)
    void testDelete() {
        when(repository.existsById("TWD")).thenReturn(true);

        service.delete("TWD");

        verify(repository).deleteById("TWD");
    }
}

