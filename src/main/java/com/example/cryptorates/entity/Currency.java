package com.example.cryptorates.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Currency {

    @Id
    private String code;
    private String symbol;
    private String enName;
    private String zhName;

}