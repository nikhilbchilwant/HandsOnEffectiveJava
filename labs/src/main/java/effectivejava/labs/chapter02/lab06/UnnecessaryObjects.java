package effectivejava.labs.chapter02.lab06;

import java.util.regex.Pattern;

/**
 * ============================================================================
 * LAB 06: Avoid Creating Unnecessary Objects (Item 6)
 * ============================================================================
 * Chapter 2, pp. 22-26
 * 
 * SCENARIO:
 * Performance-critical code creates objects unnecessarily:
 * - String literals created with new String()
 * - Pattern compiled every time in String.matches()
 * - Autoboxing creates millions of objects
 * 
 * YOUR TASK:
 * TODO #1: Remove unnecessary String constructor
 * TODO #2: Cache the compiled Pattern as a static final
 * TODO #3: Use primitive long instead of boxed Long
 * ============================================================================
 */
public class UnnecessaryObjects {

    // =========================================================================
    // PROBLEM 1: Unnecessary String objects
    // =========================================================================
    
    public void stringProblem() {
        // FIXME: DON'T DO THIS! Creates a new String object unnecessarily
        String s = new String("bikini");  // BAD
        
        // TODO: Fix to:
        // String s = "bikini";  // Uses string pool, no new object
    }

    // =========================================================================
    // PROBLEM 2: Pattern compiled every call
    // =========================================================================
    
    // FIXME: This compiles the regex EVERY TIME isRomanNumeral is called!
    public static boolean isRomanNumeral(String s) {
        return s.matches("^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
    }

    // TODO: Cache the compiled pattern:
    // private static final Pattern ROMAN = Pattern.compile(
    //     "^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
    //
    // public static boolean isRomanNumeral(String s) {
    //     return ROMAN.matcher(s).matches();
    // }

    // =========================================================================
    // PROBLEM 3: Autoboxing creates unnecessary objects
    // =========================================================================
    
    // FIXME: This creates ~2 billion Long objects!
    public static long sumBad() {
        Long sum = 0L;  // BOXED - every += creates a new Long!
        for (long i = 0; i <= Integer.MAX_VALUE; i++) {
            sum += i;  // Unbox, add, re-box... 2 billion times!
        }
        return sum;
    }

    // TODO: Fix by using primitive:
    // public static long sumGood() {
    //     long sum = 0L;  // primitive
    //     for (long i = 0; i <= Integer.MAX_VALUE; i++) {
    //         sum += i;  // No boxing overhead
    //     }
    //     return sum;
    // }

    public static void main(String[] args) {
        System.out.println("=== Unnecessary Object Creation ===\n");

        // Test 1: Roman numeral check
        System.out.println("Testing Roman numeral pattern...");
        String[] tests = {"MCMLXXVI", "MMXXI", "INVALID", "XIV"};
        long start = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            for (String test : tests) {
                isRomanNumeral(test);
            }
        }
        long elapsed = System.nanoTime() - start;
        System.out.printf("Time: %.2f ms (compiles Pattern every call!)%n", elapsed / 1_000_000.0);

        // Test 2: Sum with autoboxing (DON'T actually run - too slow!)
        System.out.println("\nAutoboxing sum (reduced iteration)...");
        Long sum = 0L;
        start = System.nanoTime();
        for (long i = 0; i < 1_000_000; i++) {
            sum += i;
        }
        elapsed = System.nanoTime() - start;
        System.out.printf("Boxed sum time: %.2f ms%n", elapsed / 1_000_000.0);

        // Compare with primitive
        long sumPrim = 0L;
        start = System.nanoTime();
        for (long i = 0; i < 1_000_000; i++) {
            sumPrim += i;
        }
        elapsed = System.nanoTime() - start;
        System.out.printf("Primitive sum time: %.2f ms%n", elapsed / 1_000_000.0);

        System.out.println("\nLesson: Prefer primitives, cache expensive objects!");
    }
}
