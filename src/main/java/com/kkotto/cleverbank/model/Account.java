package com.kkotto.cleverbank.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account {
    private Long id;
    private BigDecimal balance;
    private Long customerId;
    private Long bankId;
}
