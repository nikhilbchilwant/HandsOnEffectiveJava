package effectivejava.labs.chapter09.lab61;

import java.util.*;

/**
 * ============================================================================
 * LAB 61: Prefer Primitive Types to Boxed Primitives (Item 61)
 * ============================================================================
 * Chapter 9, pp. 273-276
 * 
 * SCENARIO:
 * Code uses boxed primitives (Integer, Long) where primitives would work.
 * This causes performance issues and subtle bugs with null and ==.
 * 
 * YOUR TASK:
 * TODO #1: Use primitives for local variables and collections values
 * TODO #2: Be careful comparing boxed primitives with ==
 * TODO #3: Watch for auto-unboxing of null values
 * ============================================================================
 */
public class BoxingProblems {

    // =========================================================================
    // BUG 1: Comparing boxed with ==
    // =========================================================================
    
    public static int compareBad(Integer first, Integer second) {
        // FIXME: Using == on boxed primitives compares REFERENCES!
        return first < second ? -1 : (first == second ? 0 : 1);
        // first == second compares references, not values!
        
        // TODO: Fix with:
        // return first.compareTo(second);
        // or: Integer.compare(first, second)
    }

    // =========================================================================
    // BUG 2: Auto-unboxing null throws NPE
    // =========================================================================
    
    public static void nullUnboxing() {
        Integer boxed = null;
        
        // FIXME: This throws NullPointerException!
        int primitive = boxed;  // Auto-unboxing null!
    }

    // =========================================================================
    // BUG 3: Hidden boxing causes performance issue
    // =========================================================================
    
    public static long sumBad() {
        Long sum = 0L;  // BOXED! Creates millions of objects
        for (long i = 0; i < 1_000_000; i++) {
            sum += i;  // Unbox, add, re-box each iteration!
        }
        return sum;
    }

    // TODO: Fix:
    // public static long sumGood() {
    //     long sum = 0L;  // primitive
    //     for (long i = 0; i < 1_000_000; i++) {
    //         sum += i;
    //     }
    //     return sum;
    // }

    public static void main(String[] args) {
        System.out.println("=== Boxing Problems Demo ===\n");

        // BUG 1: Reference comparison
        System.out.println("Comparing 42 and 42:");
        Integer a = 42;
        Integer b = 42;
        System.out.println("a == b: " + (a == b));  // MAY be true (caching -128 to 127)
        
        Integer c = 200;
        Integer d = 200;
        System.out.println("200 == 200: " + (c == d));  // FALSE! Different objects!

        // BUG 2: Null unboxing
        System.out.println("\nNull unboxing:");
        try {
            nullUnboxing();
        } catch (NullPointerException e) {
            System.out.println("NPE when unboxing null!");
        }

        // BUG 3: Performance
        System.out.println("\nPerformance comparison:");
        long start = System.nanoTime();
        sumBad();
        long badTime = System.nanoTime() - start;
        System.out.println("Boxed sum: " + badTime / 1_000_000 + " ms");

        System.out.println("\n--- Rules ---");
        System.out.println("1. Use primitives when possible");
        System.out.println("2. Never use == on boxed primitives");
        System.out.println("3. Watch for null unboxing");
    }
}
