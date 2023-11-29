package com.kkotto.cleverbank.model;

import lombok.Data;

@Data
public class Customer {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
}
