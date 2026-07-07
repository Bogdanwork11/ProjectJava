package com.example.databasework.dto;

import java.math.BigDecimal;

public class InfoDtoTranz {
    private String owner;
    private BigDecimal balance;

    public String getOwner() {
        return owner;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
