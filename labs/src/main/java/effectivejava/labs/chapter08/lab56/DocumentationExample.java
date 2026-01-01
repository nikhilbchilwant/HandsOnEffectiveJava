package effectivejava.labs.chapter08.lab56;

/**
 * ============================================================================
 * LAB 56: Write Doc Comments for All Exposed API Elements (Item 56)
 * ============================================================================
 * Chapter 8, pp. 254-260
 * 
 * SCENARIO:
 * Public API lacks documentation. Users don't know how to use it properly.
 * 
 * YOUR TASK:
 * TODO: Add proper Javadoc to all public API elements
 * ============================================================================
 */
public class DocumentationExample {

    // =========================================================================
    // BAD: No documentation
    // =========================================================================

    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // =========================================================================
    // GOOD: Properly documented
    // =========================================================================

    /**
     * Returns the greatest common divisor of two integers.
     * 
     * <p>The GCD is the largest positive integer that divides both
     * {@code a} and {@code b} without a remainder.
     *
     * @param a the first integer
     * @param b the second integer
     * @return the greatest common divisor of {@code a} and {@code b}
     * @throws ArithmeticException if both {@code a} and {@code b} are zero
     */
    public static int gcdDocumented(int a, int b) {
        if (a == 0 && b == 0) {
            throw new ArithmeticException("gcd(0, 0) is undefined");
        }
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // =========================================================================
    // Documentation guidelines
    // =========================================================================

    /**
     * A geometric shape with an area.
     * 
     * <p>Implementations must be immutable.
     * 
     * @implSpec The default implementation returns 0.
     */
    public interface Shape {
        /**
         * Returns the area of this shape.
         * 
         * @return the area, always non-negative
         */
        default double area() { return 0; }
    }

    /**
     * Enum of supported colors.
     */
    public enum Color {
        /** Primary red (#FF0000). */
        RED,
        /** Primary green (#00FF00). */
        GREEN,
        /** Primary blue (#0000FF). */
        BLUE
    }

    public static void main(String[] args) {
        System.out.println("=== Documentation Best Practices ===\n");

        System.out.println("Every public element needs Javadoc:");
        System.out.println("- Classes and interfaces");
        System.out.println("- Methods (all params, return, throws)");
        System.out.println("- Fields (especially public static final)");
        System.out.println("- Enum constants");

        System.out.println("\nKey tags:");
        System.out.println("  @param    - describe each parameter");
        System.out.println("  @return   - describe return value");
        System.out.println("  @throws   - document each exception");
        System.out.println("  @implSpec - implementation requirements");
        System.out.println("  {@code x} - code formatting");
        System.out.println("  {@link X} - link to other element");

        System.out.println("\nFirst sentence is the SUMMARY - make it count!");
    }
}
