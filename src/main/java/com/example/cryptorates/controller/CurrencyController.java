package com.example.cryptorates.controller;

import com.example.cryptorates.dto.request.CurrencyRateRequest;
import com.example.cryptorates.dto.response.ApiResponse;
import com.example.cryptorates.dto.response.CurrencyResponse;
import com.example.cryptorates.service.CurrencyService;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {

    private final CurrencyService service;

    public CurrencyController(CurrencyService service) {
        this.service = service;
    }

    /**
     * 查詢所有幣別資料
     *
     * @return ApiResponse 包裝的幣別列表
     */
    @GetMapping
    public ApiResponse<List<CurrencyResponse>> findAll() {
        return ApiResponse.success(service.findAll());
    }

    /**
     * 查詢單一幣別資料（依據代碼）
     *
     * @param id 幣別代碼（如 USD）
     * @return ApiResponse 包裝的單一幣別資料
     */
    @GetMapping("/{id}")
    public ApiResponse<CurrencyResponse> findOne(@PathVariable @NotBlank String id) {
        return ApiResponse.success(service.findOne(id));
    }

    /**
     * 新增幣別資料
     *
     * @param request 幣別新增請求資料（包含 code、名稱、匯率等）
     * @return ApiResponse 包裝的新增結果（回傳新增後的實體）
     */
    @PostMapping
    public ApiResponse<CurrencyResponse> create(@RequestBody CurrencyRateRequest request) {
        return ApiResponse.success(service.create(request));
    }

    /**
     * 更新指定幣別資料
     *
     * @param id      幣別代碼（或識別碼）
     * @param request 幣別更新請求資料
     * @return ApiResponse 包裝的更新後結果
     */
    @PutMapping("/{id}")
    public ApiResponse<CurrencyResponse> update(@PathVariable String id, @RequestBody CurrencyRateRequest request) {
        CurrencyResponse currencyRate = service.update(request);
        return ApiResponse.success(currencyRate);
    }

    /**
     * 刪除指定幣別資料
     *
     * @param id 幣別代碼（或識別碼）
     * @return ApiResponse<Void> 回傳 null 表示成功
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ApiResponse.success(null);
    }
}
