package effectivejava.labs.chapter09.lab60;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * ============================================================================
 * LAB 60: Avoid float and double for Exact Answers (Item 60)
 * ============================================================================
 * Chapter 9, pp. 270-273
 * 
 * SCENARIO:
 * Financial calculations use double, causing subtle rounding errors.
 * For money: use BigDecimal, int, or long (in cents).
 * 
 * YOUR TASK:
 * TODO: Replace double with BigDecimal or long for money
 * ============================================================================
 */
public class FloatDoubleProblem {

    // =========================================================================
    // BAD: Using double for money
    // =========================================================================
    
    public static void buyCandy() {
        double funds = 1.00;
        int itemsBought = 0;

        // Buy candies at 10c, 20c, 30c, ...
        for (double price = 0.10; funds >= price; price += 0.10) {
            funds -= price;
            itemsBought++;
        }

        System.out.printf("Items bought: %d%n", itemsBought);
        System.out.printf("Change: $%.2f%n", funds);
        // WRONG! Shows change of $0.39999999999999...
    }

    // =========================================================================
    // GOOD: Using BigDecimal
    // =========================================================================
    
    public static void buyCandyBigDecimal() {
        BigDecimal funds = new BigDecimal("1.00");
        int itemsBought = 0;

        for (BigDecimal price = new BigDecimal("0.10");
             funds.compareTo(price) >= 0;
             price = price.add(new BigDecimal("0.10"))) {
            funds = funds.subtract(price);
            itemsBought++;
        }

        System.out.printf("Items bought: %d%n", itemsBought);
        System.out.printf("Change: $%s%n", funds);  // Exact!
    }

    // =========================================================================
    // GOOD: Using long (cents)
    // =========================================================================
    
    public static void buyCandyCents() {
        int funds = 100;  // cents
        int itemsBought = 0;

        for (int price = 10; funds >= price; price += 10) {
            funds -= price;
            itemsBought++;
        }

        System.out.printf("Items bought: %d%n", itemsBought);
        System.out.printf("Change: $%.2f%n", funds / 100.0);  // Exact!
    }

    public static void main(String[] args) {
        System.out.println("=== float/double vs exact arithmetic ===\n");

        // Demonstrate the problem
        System.out.println("1.03 - 0.42 = " + (1.03 - 0.42));  // Not 0.61!

        System.out.println("\n--- Candy problem with double (WRONG) ---");
        buyCandy();

        System.out.println("\n--- Candy problem with BigDecimal (CORRECT) ---");
        buyCandyBigDecimal();

        System.out.println("\n--- Candy problem with cents (CORRECT) ---");
        buyCandyCents();

        System.out.println("\n--- Guidelines ---");
        System.out.println("❌ double/float: for display, not exact calculations");
        System.out.println("✅ BigDecimal: for money if precision needed");
        System.out.println("✅ int/long: for money in cents (faster)");
    }
}
