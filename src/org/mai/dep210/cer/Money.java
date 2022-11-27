package org.mai.dep210.cer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public class Money {


    private Currency currency;
    private BigDecimal amount;

    public Money(Currency currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount.setScale(this.currency.getDefaultFractionDigits());
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Money add(Money m) {
        checkCurrencies(m);
        return new Money(this.currency, amount.add(m.getAmount()));
    }

    public Money subtract(Money m) {
        checkCurrencies(m);
        return new Money(this.currency, amount.subtract(m.getAmount()));
    }

    public Money multiply(BigDecimal ratio) {
        return new Money(this.currency, amount.multiply(ratio).setScale(currency.getDefaultFractionDigits(), RoundingMode.HALF_UP));
    }

    public Money divide(BigDecimal ratio) {
        return new Money(this.currency, amount.divide(ratio, RoundingMode.FLOOR));
    }

    private void checkCurrencies(Money m) {
        if (!this.currency.equals(m.getCurrency())) {
            throw new DifferentCurrenciesException(
                    String.format("Different currencies: [%s] and [%s]", this.currency, m.getCurrency())
            );
        }
    }
}
