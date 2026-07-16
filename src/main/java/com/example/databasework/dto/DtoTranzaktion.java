package com.example.databasework.dto;

import java.math.BigDecimal;

public class DtoTranzaktion{
    private Integer from;
    private Integer to;
    private BigDecimal amount;

    public Integer getFrom() {
        return from;
    }

    public Integer getTo() {
        return to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
