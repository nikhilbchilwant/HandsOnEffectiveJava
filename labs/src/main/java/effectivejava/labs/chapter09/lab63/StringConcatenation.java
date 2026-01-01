package effectivejava.labs.chapter09.lab63;

/**
 * ============================================================================
 * LAB 63: Beware the Performance of String Concatenation (Item 63)
 * ============================================================================
 * Chapter 9, pp. 279-280
 * 
 * SCENARIO:
 * Building strings with + in a loop creates many temporary String objects.
 * Use StringBuilder instead.
 * 
 * YOUR TASK:
 * TODO: Replace + concatenation in loops with StringBuilder
 * ============================================================================
 */
public class StringConcatenation {

    // =========================================================================
    // BAD: String concatenation in loop - O(n²) time!
    // =========================================================================

    public String buildStatementBad(int count) {
        String result = "";
        for (int i = 0; i < count; i++) {
            result += "Line " + i + "\n";  // Creates new String each time!
        }
        return result;
        // Each += copies the entire existing string!
        // Total characters copied: 1 + 2 + 3 + ... + n = O(n²)
    }

    // =========================================================================
    // GOOD: StringBuilder - O(n) time
    // =========================================================================

    public String buildStatementGood(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append("Line ").append(i).append("\n");
        }
        return sb.toString();
        // StringBuilder just appends to internal buffer
        // Total time: O(n)
    }

    // =========================================================================
    // Note: Single-line concatenation is FINE
    // =========================================================================

    public String singleLine(String first, String last) {
        // This is efficient - compiler uses StringBuilder
        return "Hello, " + first + " " + last + "!";
    }

    public static void main(String[] args) {
        StringConcatenation demo = new StringConcatenation();

        int iterations = 10000;

        System.out.println("=== String Concatenation Performance ===\n");

        // Measure bad approach
        long start = System.nanoTime();
        demo.buildStatementBad(iterations);
        long badTime = System.nanoTime() - start;
        System.out.printf("String += in loop (%d iters): %.2f ms%n",
                iterations, badTime / 1_000_000.0);

        // Measure good approach
        start = System.nanoTime();
        demo.buildStatementGood(iterations);
        long goodTime = System.nanoTime() - start;
        System.out.printf("StringBuilder (%d iters): %.2f ms%n",
                iterations, goodTime / 1_000_000.0);

        System.out.printf("\nSpeedup: %.1fx faster!%n", (double) badTime / goodTime);

        System.out.println("\n--- Rule ---");
        System.out.println("Use + for simple one-liners");
        System.out.println("Use StringBuilder for loops");
    }
}
