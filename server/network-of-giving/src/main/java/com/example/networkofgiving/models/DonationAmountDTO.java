package com.example.networkofgiving.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;

public class DonationAmountDTO implements Serializable {

    @NotNull
    @Positive
    private BigDecimal amount;

    public DonationAmountDTO(@NotNull @Positive BigDecimal amount) {
        this.amount = amount;
    }

    public DonationAmountDTO() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
