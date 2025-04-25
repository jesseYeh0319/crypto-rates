package com.example.cryptorates.service;

import com.example.cryptorates.dto.request.CurrencyRateRequest;
import com.example.cryptorates.dto.response.CurrencyResponse;
import com.example.cryptorates.entity.Currency;
import com.example.cryptorates.exception.DataNotFoundException;
import com.example.cryptorates.exception.ResourceConflictException;
import com.example.cryptorates.repository.CurrencyRepository;
import com.example.cryptorates.util.CurrencyRateMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository repository;

    public CurrencyServiceImpl(CurrencyRepository repository) {
        this.repository = repository;
    }

    /**
     * 查詢所有幣別資料，並轉換為回應格式
     *
     * @return 幣別回應列表
     */
    @Override
    public List<CurrencyResponse> findAll() {
        List<Currency> currencies = repository.findAll();
        return toResponseList(currencies);
    }

    /**
     * 根據幣別代碼查詢單一資料（代碼不區分大小寫）
     *
     * @param code 幣別代碼（例如 USD）
     * @return 幣別回應物件
     * @throws DataNotFoundException 找不到資料時拋出
     */
    @Override
    public CurrencyResponse findOne(String code) {
        Currency currency = findOrThrow(code);
        return CurrencyRateMapper.toVo(currency);
    }

    /**
     * 建立新的幣別資料
     *
     * @param request 幣別資料建立請求
     * @return 建立成功的資料實體
     * @throws ResourceConflictException 若幣別已存在則拋出
     */
    @Override
    public CurrencyResponse create(CurrencyRateRequest request) {
        String code = normalizeCode(request.getCode());

        if (repository.existsById(code)) {
            throw new ResourceConflictException("幣別已存在，不可重複建立");
        }

        Currency entity = CurrencyRateMapper.toEntity(request);
        entity.setCode(code); // 保證 code 一律轉為大寫儲存
        return CurrencyRateMapper.toVo(repository.save(entity));
    }

    /**
     * 更新現有的幣別資料（只更新部份欄位）
     *
     * @param request 幣別資料更新請求
     * @return 更新後的資料實體
     * @throws DataNotFoundException 若找不到該幣別則拋出
     */
    @Override
    public CurrencyResponse update(CurrencyRateRequest request) {
        String code = normalizeCode(request.getCode());
        Currency currency = findOrThrow(code);

        // 更新欄位內容
        currency.setSymbol(request.getSymbol());
        currency.setEnName(request.getEnName());
        currency.setZhName(request.getZhName());
        return CurrencyRateMapper.toVo(repository.save(currency));
    }

    /**
     * 根據幣別代碼刪除資料
     *
     * @param code 幣別代碼
     * @throws DataNotFoundException 若找不到資料則拋出
     */
    @Override
    public void delete(String code) {
        String normalized = normalizeCode(code);

        if (!repository.existsById(normalized)) {
            throw new DataNotFoundException("找不到幣別: " + code);
        }

        repository.deleteById(normalized);
    }


    /**
     * 依據幣別代碼查詢，若查無資料則拋出錯誤
     *
     * @param code 幣別代碼
     * @return 查到的資料實體
     */
    private Currency findOrThrow(String code) {
        return repository.findById(normalizeCode(code))
                .orElseThrow(() -> new DataNotFoundException("找不到幣別: " + code));
    }

    /**
     * 將幣別代碼轉為大寫，並去除前後空白
     *
     * @param code 原始輸入的幣別代碼
     * @return 標準化後的幣別代碼
     */
    private String normalizeCode(String code) {
        return code != null ? code.trim().toUpperCase() : "";
    }

    /**
     * 將實體列表轉為回應物件列表
     *
     * @param entities 資料庫實體列表
     * @return 回應物件列表
     */
    private List<CurrencyResponse> toResponseList(List<Currency> entities) {
        return entities.stream()
                .map(CurrencyRateMapper::toVo)
                .collect(Collectors.toList());
    }
}


