package effectivejava.labs.chapter06.lab38;

import java.util.*;

/**
 * ============================================================================
 * LAB 38: Emulate Extensible Enums with Interfaces (Item 38)
 * ============================================================================
 * Chapter 6, pp. 176-180
 * 
 * SCENARIO:
 * You need extensible enums (like operations that users can add to).
 * Enums can't extend other enums, but they CAN implement interfaces!
 * 
 * YOUR TASK:
 * TODO: Create an interface for the operation, implement in enum
 * ============================================================================
 */
public class ExtensibleEnums {

    // =========================================================================
    // Step 1: Define interface for the operation
    // =========================================================================

    interface Operation {
        double apply(double x, double y);
    }

    // =========================================================================
    // Step 2: Base operations as enum implementing the interface
    // =========================================================================

    enum BasicOperation implements Operation {
        PLUS("+") {
            public double apply(double x, double y) { return x + y; }
        },
        MINUS("-") {
            public double apply(double x, double y) { return x - y; }
        },
        TIMES("*") {
            public double apply(double x, double y) { return x * y; }
        },
        DIVIDE("/") {
            public double apply(double x, double y) { return x / y; }
        };

        private final String symbol;

        BasicOperation(String symbol) { this.symbol = symbol; }

        @Override
        public String toString() { return symbol; }
    }

    // =========================================================================
    // Step 3: Extended operations as ANOTHER enum, same interface
    // =========================================================================

    enum ExtendedOperation implements Operation {
        EXP("^") {
            public double apply(double x, double y) {
                return Math.pow(x, y);
            }
        },
        REMAINDER("%") {
            public double apply(double x, double y) {
                return x % y;
            }
        };

        private final String symbol;

        ExtendedOperation(String symbol) { this.symbol = symbol; }

        @Override
        public String toString() { return symbol; }
    }

    // =========================================================================
    // Generic method that works with ANY Operation enum
    // =========================================================================

    private static <T extends Enum<T> & Operation> void test(
            Class<T> opEnumType, double x, double y) {
        for (Operation op : opEnumType.getEnumConstants()) {
            System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Extensible Enums ===\n");

        double x = 4.0, y = 2.0;

        System.out.println("Basic operations:");
        test(BasicOperation.class, x, y);

        System.out.println("\nExtended operations:");
        test(ExtendedOperation.class, x, y);

        System.out.println("\n--- Pattern ---");
        System.out.println("1. Define interface: Operation");
        System.out.println("2. Base enum implements it: BasicOperation");
        System.out.println("3. Extension enum implements it: ExtendedOperation");
        System.out.println("4. Use bounded type: <T extends Enum<T> & Operation>");
    }
}
