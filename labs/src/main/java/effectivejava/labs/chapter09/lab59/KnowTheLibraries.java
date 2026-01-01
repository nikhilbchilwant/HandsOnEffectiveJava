package effectivejava.labs.chapter09.lab59;

import java.util.*;
import java.util.concurrent.*;

/**
 * ============================================================================
 * LAB 59: Know and Use the Libraries (Item 59)
 * ============================================================================
 * Chapter 9, pp. 267-270
 * 
 * SCENARIO:
 * Developers reinvent wheels instead of using standard library methods.
 * 
 * YOUR TASK:
 * TODO: Replace custom implementations with library methods
 * ============================================================================
 */
public class KnowTheLibraries {

    // =========================================================================
    // BAD: Reinventing random number generation
    // =========================================================================

    // Broken - flawed random number generator!
    static Random rnd = new Random();
    
    static int randomBad(int n) {
        return Math.abs(rnd.nextInt()) % n;
        // BUGS:
        // 1. If nextInt() returns Integer.MIN_VALUE, Math.abs returns negative!
        // 2. Biased toward lower numbers for non-powers of 2
    }

    // =========================================================================
    // GOOD: Use library method
    // =========================================================================

    static int randomGood(int n) {
        return rnd.nextInt(n);  // Correct, unbiased, fast
    }

    // Even better in Java 17+:
    // RandomGenerator rng = RandomGenerator.of("L64X128MixRandom");

    // =========================================================================
    // More examples: Know your libraries!
    // =========================================================================

    public void libraryExamples() {
        // DON'T: Manual string joining
        // String result = "";
        // for (String s : list) result += s + ",";
        
        // DO: Use String.join
        List<String> list = List.of("a", "b", "c");
        String joined = String.join(", ", list);

        // DON'T: Manual array copy
        // for (int i = 0; i < src.length; i++) dst[i] = src[i];
        
        // DO: Use Arrays.copyOf or System.arraycopy
        int[] src = {1, 2, 3};
        int[] dst = Arrays.copyOf(src, src.length);

        // DON'T: Check if string is empty
        // if (s.length() == 0)
        
        // DO: Use isEmpty()
        String s = "";
        if (s.isEmpty()) { /* ... */ }

        // DON'T: Create thread manually for tasks
        // new Thread(() -> task()).start();
        
        // DO: Use ExecutorService
        ExecutorService exec = Executors.newCachedThreadPool();
        // exec.submit(() -> task());
        exec.shutdown();
    }

    // =========================================================================
    // Key libraries to know
    // =========================================================================

    public static void main(String[] args) {
        System.out.println("=== Know the Libraries ===\n");

        // Demonstrate the random bug
        int n = 2 * (Integer.MAX_VALUE / 3);
        int low = 0;
        for (int i = 0; i < 1_000_000; i++) {
            if (randomBad(n) < n / 2) low++;
        }
        System.out.printf("Bad random: %d%% below midpoint (should be ~50%%)%n", 
                low / 10_000);

        System.out.println("\n--- Key Libraries to Know ---");
        System.out.println("java.lang, java.util, java.io");
        System.out.println("java.util.stream (streams)");
        System.out.println("java.util.concurrent (concurrency)");
        System.out.println("java.util.function (functional interfaces)");
        System.out.println("java.time (dates/times)");

        System.out.println("\n--- Benefits ---");
        System.out.println("1. Correct (tested by millions)");
        System.out.println("2. Faster (experts optimize them)");
        System.out.println("3. Improved over time (you get updates free)");
    }
}
