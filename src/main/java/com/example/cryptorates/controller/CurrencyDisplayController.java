package com.example.cryptorates.controller;

import com.example.cryptorates.dto.response.ApiResponse;
import com.example.cryptorates.dto.response.CoinDeskTransformedResponse;
import com.example.cryptorates.service.CurrencyViewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CurrencyDisplayController {

    private final CurrencyViewService currencyViewService;

    public CurrencyDisplayController(CurrencyViewService currencyViewService) {
        this.currencyViewService = currencyViewService;
    }

    /**
     * 取得轉換後的匯率資料（含中文名稱、更新時間）
     *
     * @return CoinDeskTransformedResponse
     */
    @GetMapping("/exchange-rates")
    public ApiResponse<CoinDeskTransformedResponse> getExchangeRate() {
        try {
            return ApiResponse.success(currencyViewService.getConvertedView());
        } catch (Exception e) {
            return ApiResponse.fail(500, "取得匯率失敗");
        }
    }
}

