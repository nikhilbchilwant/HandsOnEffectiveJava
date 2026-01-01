package effectivejava.labs.chapter09.lab62;

import java.util.*;

/**
 * ============================================================================
 * LAB 62: Avoid Strings Where Other Types Are More Appropriate (Item 62)
 * ============================================================================
 * Chapter 9, pp. 276-279
 * 
 * SCENARIO:
 * Strings are overused as a universal data type. Better alternatives exist!
 * 
 * YOUR TASK:
 * TODO: Replace String with more appropriate types
 * ============================================================================
 */
public class StringOveruse {

    // =========================================================================
    // BAD: String as enum
    // =========================================================================

    // DON'T: Magic string constants
    public void processStatusBad(String status) {
        if (status.equals("ACTIVE")) {
            // ...
        } else if (status.equals("PENDING")) {
            // ...
        }
        // Typos like "active" or "Active" are silent bugs!
    }

    // GOOD: Use enum
    enum Status { ACTIVE, PENDING, CLOSED }

    public void processStatusGood(Status status) {
        switch (status) {
            case ACTIVE -> { }
            case PENDING -> { }
            case CLOSED -> { }
        }
        // Type-safe, IDE auto-complete, compiler-checked
    }

    // =========================================================================
    // BAD: String as aggregate type
    // =========================================================================

    // DON'T: Compound data in a string
    String compoundKey = "className#12345#methodName";
    // To access parts, must parse - error-prone!

    // GOOD: Use a proper class
    record CompoundKey(String className, int id, String methodName) { }
    // Type-safe, self-documenting

    // =========================================================================
    // BAD: String as capability/key
    // =========================================================================

    // DON'T: String as map key for capabilities
    Map<String, Object> capabilities = new HashMap<>();

    void setBad() {
        capabilities.put("userSession", new Object());
        // Any code can overwrite with same string!
    }

    // GOOD: Use a class as the key
    static class Key<T> {
        private final String name;
        Key(String name) { this.name = name; }
    }
    // Only holder of Key instance can access

    // =========================================================================
    // BAD: String holds numeric data
    // =========================================================================

    String priceStr = "19.99";  // DON'T: Use BigDecimal!
    String countStr = "42";     // DON'T: Use int!

    public static void main(String[] args) {
        System.out.println("=== String Overuse ===\n");

        System.out.println("Strings are overused as:");
        System.out.println("1. Enums → Use actual enum types");
        System.out.println("2. Aggregate types → Use classes/records");
        System.out.println("3. Capabilities → Use unforgeable keys");
        System.out.println("4. Numbers → Use int, long, BigDecimal");

        System.out.println("\nProblems with String:");
        System.out.println("- No compile-time checking");
        System.out.println("- No namespace protection");
        System.out.println("- Must parse for compound data");
        System.out.println("- Typos are silent bugs");
    }
}
