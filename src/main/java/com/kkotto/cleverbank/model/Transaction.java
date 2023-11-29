package com.kkotto.cleverbank.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Transaction {
    private Long id;
    private Long senderCustomerId;
    private Long receiverCustomerId;
    private BigDecimal amount;
    private Long senderAccountId;
    private Long receiverAccountId;
    private Long senderBankId;
    private Long receiverBankId;
    private String comment;
}
