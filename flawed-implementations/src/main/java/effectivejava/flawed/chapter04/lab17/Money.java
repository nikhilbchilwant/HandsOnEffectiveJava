package effectivejava.flawed.chapter04.lab17;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * FLAWED IMPLEMENTATION - Mutable Money class
 * 
 * This money class is mutable, which leads to several problems:
 * - Not thread-safe (concurrent modifications)
 * - Can be changed unexpectedly when passed around
 * - Cannot be safely used as a map key
 * - Harder to reason about
 */
public class Money {

    private BigDecimal amount;  // NOT final - can be modified!
    private Currency currency;  // NOT final!

    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Money(double amount, String currencyCode) {
        this(BigDecimal.valueOf(amount), Currency.getInstance(currencyCode));
    }

    // PROBLEM: Getters return mutable state
    public BigDecimal getAmount() { 
        return amount; 
    }

    public Currency getCurrency() { 
        return currency; 
    }

    // PROBLEM: Setters allow external modification!
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    // PROBLEM: Modifies this object instead of returning new instance!
    public void add(Money other) {
        requireSameCurrency(other);
        this.amount = this.amount.add(other.amount);
    }

    // PROBLEM: Same issue - modifies in place
    public void subtract(Money other) {
        requireSameCurrency(other);
        this.amount = this.amount.subtract(other.amount);
    }

    // PROBLEM: Modifies in place
    public void multiply(BigDecimal multiplier) {
        this.amount = this.amount.multiply(multiplier);
    }

    private void requireSameCurrency(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException(
                "Cannot combine " + this.currency + " with " + other.currency);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money money)) return false;
        return amount.equals(money.amount) && currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return 31 * amount.hashCode() + currency.hashCode();
    }

    @Override
    public String toString() {
        return currency.getSymbol() + amount;
    }

    // Consider:
    // 1. What happens if this Money is used as a HashMap key and then modified?
    // 2. What happens when two threads call add() at the same time?
    // 3. What happens if I pass this Money to a method that unexpectedly modifies it?
}
