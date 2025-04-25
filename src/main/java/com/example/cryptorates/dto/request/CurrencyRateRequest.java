package com.example.cryptorates.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CurrencyRateRequest {
    @NotBlank(message = "幣別代碼不得為空")
    @Size(max = 3, message = "幣別代碼過長")
    private String code;
    private String symbol;
    private String zhName;
    private String enName;
}
