package effectivejava.labs.chapter10.lab75;

/**
 * ============================================================================
 * LAB 75: Include Failure-Capture Information in Detail Messages (Item 75)
 * ============================================================================
 * Chapter 10, pp. 306-307
 * 
 * SCENARIO:
 * Exception messages lack the information needed to diagnose failures.
 * 
 * YOUR TASK:
 * TODO: Create informative exception messages
 * ============================================================================
 */
public class FailureCaptureInfo {

    // =========================================================================
    // BAD: Useless exception message
    // =========================================================================

    static void validateAgeBad(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Invalid age");
            // What age? What are the bounds? Useless!
        }
    }

    // =========================================================================
    // GOOD: Capture all relevant info
    // =========================================================================

    static void validateAgeGood(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException(
                    "Age out of range [0-150]: " + age);
            // Tells what value, what bounds
        }
    }

    // =========================================================================
    // BETTER: Custom exception with fields
    // =========================================================================

    static class IndexOutOfBoundsException extends RuntimeException {
        private final int lowerBound;
        private final int upperBound;
        private final int index;

        public IndexOutOfBoundsException(int lowerBound, int upperBound, int index) {
            // Generate informative message
            super(String.format("Index %d out of bounds [%d, %d)",
                    index, lowerBound, upperBound));
            
            // Also store values for programmatic access
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
            this.index = index;
        }

        public int getLowerBound() { return lowerBound; }
        public int getUpperBound() { return upperBound; }
        public int getIndex() { return index; }
    }

    static void accessElement(Object[] array, int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException(0, array.length, index);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Failure-Capture Information ===\n");

        // Bad message
        try {
            validateAgeBad(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("BAD: " + e.getMessage());
        }

        // Good message
        try {
            validateAgeGood(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("GOOD: " + e.getMessage());
        }

        // Custom exception
        try {
            accessElement(new Object[10], 15);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("CUSTOM: " + e.getMessage());
            System.out.println("  Index: " + e.getIndex());
        }

        System.out.println("\n--- What to Include ---");
        System.out.println("✓ All parameter values that caused failure");
        System.out.println("✓ Field values that contributed");
        System.out.println("✓ Expected range/constraints that were violated");
        System.out.println("✗ NOT: Passwords, keys, sensitive data");
    }
}
