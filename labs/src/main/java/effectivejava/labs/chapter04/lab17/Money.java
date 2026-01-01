package effectivejava.labs.chapter04.lab17;

import java.math.BigDecimal;

/**
 * ============================================================================
 * LAB 17: Minimize Mutability (Item 17)
 * ============================================================================
 * Chapter 4, pp. 80-87
 * 
 * SCENARIO:
 * A Money class that SHOULD be immutable but isn't. Mutable Money causes
 * bugs in multi-threaded code and surprises when used as map keys.
 * 
 * YOUR TASK:
 * TODO #1: Make class final (or make constructors private with factories)
 * TODO #2: Make all fields final
 * TODO #3: Remove all setters
 * TODO #4: Return new instances instead of modifying
 * TODO #5: Make defensive copies of mutable fields
 * ============================================================================
 */
public class Money {

    // FIXME: Fields should be final!
    private BigDecimal amount;
    private String currency;

    public Money(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }

    // =========================================================================
    // FIXME: These mutators break immutability!
    // =========================================================================
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;  // MUTATION!
    }

    public void setCurrency(String currency) {
        this.currency = currency;  // MUTATION!
    }

    // FIXME: This modifies the current object!
    public void add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        this.amount = this.amount.add(other.amount);  // MUTATION!
    }

    // =========================================================================
    // TODO: Make these return new instances instead
    // =========================================================================
    
    // public Money add(Money other) {
    //     if (!this.currency.equals(other.currency)) {
    //         throw new IllegalArgumentException("Currency mismatch");
    //     }
    //     return new Money(this.amount.add(other.amount), this.currency);
    // }
    //
    // public Money multiply(int n) {
    //     return new Money(amount.multiply(BigDecimal.valueOf(n)), currency);
    // }

    @Override
    public String toString() {
        return amount + " " + currency;
    }

    public static void main(String[] args) {
        System.out.println("=== Mutability Problem Demo ===\n");

        Money price = new Money(new BigDecimal("100.00"), "USD");
        Money originalPrice = price;  // Same reference!

        System.out.println("Original price: " + originalPrice);

        // Someone modifies it
        price.setAmount(new BigDecimal("50.00"));

        System.out.println("After 'price' modification:");
        System.out.println("originalPrice: " + originalPrice);  // Also changed!

        System.out.println("\nBUG: originalPrice was silently modified!");
        System.out.println("This breaks assumptions and causes subtle bugs.");

        System.out.println("\n--- Solution ---");
        System.out.println("Make Money immutable:");
        System.out.println("1. Make fields final");
        System.out.println("2. Remove setters");
        System.out.println("3. add() returns NEW Money");
    }
}
