package com.example.cryptorates.util;

import com.example.cryptorates.dto.request.CurrencyRateRequest;
import com.example.cryptorates.dto.response.CurrencyResponse;
import com.example.cryptorates.entity.Currency;
import org.springframework.beans.BeanUtils;

/**
 * 幣別資料轉換工具類
 * 提供 Entity、Request、Response 間的轉換功能
 */
public class CurrencyRateMapper {

    /**
     * 將 Currency 實體轉換為 CurrencyResponse（對外用的 VO）
     *
     * @param rate 資料庫中的幣別實體
     * @return CurrencyResponse
     */
    public static CurrencyResponse toVo(Currency rate) {
        CurrencyResponse currencyVo = new CurrencyResponse();
        BeanUtils.copyProperties(rate, currencyVo);
        return currencyVo;
    }

    /**
     * 將使用者送進來的 CurrencyRateRequest 轉換為 Currency 實體
     *
     * @param request 使用者建立或更新幣別資料的請求物件
     * @return Currency
     */
    public static Currency toEntity(CurrencyRateRequest request) {
        Currency entity = new Currency();
        BeanUtils.copyProperties(request, entity);
        return entity;
    }
}