package effectivejava.labs.chapter08.lab53;

/**
 * ============================================================================
 * LAB 53: Use Varargs Judiciously (Item 53) 
 * ============================================================================
 * Chapter 8, pp. 245-246
 * 
 * SCENARIO:
 * Varargs are used incorrectly - allowing zero arguments when minimum is one.
 * 
 * YOUR TASK:
 * TODO: Handle minimum argument requirements properly
 * ============================================================================
 */
public class VarargsUsage {

    // =========================================================================
    // BAD: Varargs allows zero arguments when we need at least one
    // =========================================================================

    // Want minimum of one argument
    static int minBad(int... args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Too few arguments");
        }
        int min = args[0];
        for (int i = 1; i < args.length; i++) {
            if (args[i] < min) min = args[i];
        }
        return min;
    }
    // Problems:
    // 1. Fails at runtime, not compile time
    // 2. Ugly - must validate array length

    // =========================================================================
    // GOOD: First arg explicit, rest varargs
    // =========================================================================

    static int minGood(int firstArg, int... remainingArgs) {
        int min = firstArg;  // First arg guaranteed!
        for (int arg : remainingArgs) {
            if (arg < min) min = arg;
        }
        return min;
    }
    // Benefits:
    // 1. Compile-time check for at least one arg
    // 2. No runtime length check needed
    // 3. Cleaner code

    // =========================================================================
    // PERFORMANCE: Provide overloads for common cases
    // =========================================================================

    // Varargs creates array on every call
    // For performance-critical code, provide overloads:

    public void log(String msg) {
        log(msg, new Object[0]);
    }

    public void log(String msg, Object a1) {
        log(msg, new Object[]{a1});
    }

    public void log(String msg, Object a1, Object a2) {
        log(msg, new Object[]{a1, a2});
    }

    public void log(String msg, Object a1, Object a2, Object a3) {
        log(msg, new Object[]{a1, a2, a3});
    }

    // Fallback varargs for 4+ arguments
    public void log(String msg, Object... args) {
        System.out.printf(msg + "%n", args);
    }

    public static void main(String[] args) {
        System.out.println("=== Varargs Usage ===\n");

        System.out.println("minGood(3, 1, 4, 1, 5): " + minGood(3, 1, 4, 1, 5));
        System.out.println("minGood(42): " + minGood(42));
        // minGood();  // Compile error! Good!

        System.out.println("\n--- Guidelines ---");
        System.out.println("1. Use (T first, T... rest) for minimum of one");
        System.out.println("2. Provide overloads for common arg counts (perf)");
        System.out.println("3. Varargs is fine for printf/reflection-style");
    }
}
