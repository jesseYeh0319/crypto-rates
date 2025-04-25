package com.example.cryptorates;

import com.example.cryptorates.dto.external.response.CurrencyDetail;
import com.example.cryptorates.dto.external.response.CurrencyRateResponse;
import com.example.cryptorates.dto.response.CoinDeskTransformedResponse;
import com.example.cryptorates.entity.Currency;
import com.example.cryptorates.repository.CurrencyRepository;
import com.example.cryptorates.service.CurrencyViewServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CurrencyViewServiceImplTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CurrencyViewServiceImpl currencyViewService;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(currencyViewService, "url", "https://kengp3.github.io/blog/coindesk.json");
    }

    @Test
    void getConvertedView_shouldReturnTransformedResponse() {
        // 模擬幣別對照資料
        Currency usd = new Currency();
        usd.setCode("USD");
        usd.setZhName("美元");

        Currency eur = new Currency();
        eur.setCode("EUR");
        eur.setZhName("歐元");

        List<Currency> mockCurrencyList = Arrays.asList(usd, eur);
        Mockito.when(currencyRepository.findAll()).thenReturn(mockCurrencyList);

        // 模擬 CoinDesk 回傳資料
        CurrencyDetail usdDetail = new CurrencyDetail();
        usdDetail.setCode("USD");
        usdDetail.setRate_float(BigDecimal.valueOf(123.45));

        CurrencyDetail eurDetail = new CurrencyDetail();
        eurDetail.setCode("EUR");
        eurDetail.setRate_float(BigDecimal.valueOf(234.56));

        Map<String, CurrencyDetail> bpiMap = new HashMap<>();
        bpiMap.put("USD", usdDetail);
        bpiMap.put("EUR", eurDetail);

        CurrencyRateResponse mockApiResponse = new CurrencyRateResponse();
        mockApiResponse.setBpi(bpiMap);

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(CurrencyRateResponse.class)))
                .thenReturn(mockApiResponse);

        // 呼叫被測方法
        CoinDeskTransformedResponse result = currencyViewService.getConvertedView();

        // 驗證回傳結果
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getCurrencies().size());
        Assertions.assertEquals("美元", result.getCurrencies().get("USD").getName());
        Assertions.assertEquals(BigDecimal.valueOf(123.45), result.getCurrencies().get("USD").getRate());
        Assertions.assertNotNull(result.getUpdateTime()); // 確認時間有設到
    }
}

