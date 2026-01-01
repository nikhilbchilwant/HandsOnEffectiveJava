package effectivejava.labs.chapter06.lab34;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * ============================================================================
 * LAB 34 (Part B): Constant-Specific Method Implementations (Item 34)
 * ============================================================================
 * Chapter 6, pp. 163-164
 * 
 * SCENARIO:
 * You need to implement a calculator operation enum where each constant
 * (PLUS, MINUS, TIMES, DIVIDE) performs its own operation.
 * 
 * The naive approach uses a switch statement in a single method, but this:
 * - Requires updating switch when adding new constants
 * - Compiler won't catch missing cases (without newer switch expressions)
 * - Violates Open/Closed principle
 * 
 * YOUR TASK:
 * TODO #1: Define enum constants with their symbols (+, -, *, /)
 * TODO #2: Add abstract apply(double x, double y) method
 * TODO #3: Each constant provides its own implementation
 * TODO #4: Implement fromString() for reverse lookup (symbol -> enum)
 * TODO #5: Use Optional for safe fromString return
 * 
 * BONUS:
 * - What happens if you add a new constant without implementing apply()?
 * - How does this compare to a switch-based approach?
 * ============================================================================
 */
public enum Operation {
    
    // =========================================================================
    // TODO #1 & #3: Define constants with symbol AND implementation
    // =========================================================================
    
    // Example structure:
    // PLUS("+") {
    //     @Override
    //     public double apply(double x, double y) {
    //         // TODO: return the sum
    //     }
    // },
    // MINUS("-") { ... },
    // TIMES("*") { ... },
    // DIVIDE("/") { ... };
    
    // FIXME: Replace this placeholder with proper constant-specific implementations
    PLUS("+"),
    MINUS("-"),
    TIMES("*"),
    DIVIDE("/");

    private final String symbol;

    Operation(String symbol) { 
        this.symbol = symbol; 
    }

    @Override 
    public String toString() { 
        return symbol; 
    }

    // =========================================================================
    // TODO #2: Make this abstract so each constant MUST implement
    // =========================================================================
    
    // FIXME: This switch-based implementation is the WRONG approach!
    // Add "abstract" keyword and remove the body.
    // Each constant above must then provide its own implementation.
    public double apply(double x, double y) {
        switch (this) {
            case PLUS:   return x + y;
            case MINUS:  return x - y;
            case TIMES:  return x * y;
            case DIVIDE: return x / y;
            default: throw new AssertionError("Unknown op: " + this);
        }
    }

    // =========================================================================
    // TODO #4 & #5: Implement fromString() with Optional
    // =========================================================================
    
    // Hint: Build a Map<String, Operation> from values()
    // private static final Map<String, Operation> stringToEnum = ...
    
    // public static Optional<Operation> fromString(String symbol) {
    //     // TODO: Return Optional.ofNullable(map lookup)
    // }

    public static void main(String[] args) {
        System.out.println("=== Constant-Specific Methods Lab ===\n");

        double x = 4.0;
        double y = 2.0;

        // Test each operation
        for (Operation op : Operation.values()) {
            System.out.printf("%.1f %s %.1f = %.1f%n", x, op, y, op.apply(x, y));
        }

        System.out.println("\n--- Current Issues ---");
        System.out.println("1. apply() uses switch - won't catch missing cases");
        System.out.println("2. No fromString() for reverse lookup");
        
        System.out.println("\n--- After Refactoring ---");
        System.out.println("1. Abstract apply() - compiler enforces implementation");
        System.out.println("2. fromString() returns Optional<Operation>");
        
        // TODO: Uncomment after implementing fromString()
        // System.out.println("\n--- Testing fromString ---");
        // fromString("+").ifPresent(op -> 
        //     System.out.println("Found + as: " + op.name()));
        // fromString("?").ifPresentOrElse(
        //     op -> System.out.println("Found: " + op),
        //     () -> System.out.println("Symbol '?' not found"));
    }
}
