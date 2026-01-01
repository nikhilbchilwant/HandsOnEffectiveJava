package effectivejava.labs.chapter07.lab42;

import java.util.*;
import java.util.function.*;

/**
 * ============================================================================
 * LAB 42: Prefer Lambdas to Anonymous Classes (Item 42)
 * ============================================================================
 * Chapter 7, pp. 193-197
 * 
 * SCENARIO:
 * Old code uses anonymous inner classes for function objects.
 * Lambdas are more concise and readable (usually).
 * 
 * YOUR TASK:
 * TODO: Replace anonymous classes with lambdas where appropriate
 * ============================================================================
 */
public class LambdaVsAnonymous {

    // =========================================================================
    // BEFORE: Verbose anonymous class
    // =========================================================================
    
    public void sortWithAnonymous(List<String> words) {
        Collections.sort(words, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return Integer.compare(s1.length(), s2.length());
            }
        });
    }

    // =========================================================================
    // AFTER: Concise lambda
    // =========================================================================
    
    public void sortWithLambda(List<String> words) {
        // TODO: Replace anonymous class with lambda:
        Collections.sort(words, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
        
        // Or even better with Comparator factory:
        // words.sort(Comparator.comparingInt(String::length));
    }

    // =========================================================================
    // More examples
    // =========================================================================
    
    public void processWithAnonymous() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello from anonymous!");
            }
        };
        new Thread(r).start();
    }

    public void processWithLambda() {
        // Much cleaner!
        new Thread(() -> System.out.println("Hello from lambda!")).start();
    }

    // When NOT to use lambdas:
    // 1. Need 'this' reference to the function object itself
    // 2. Need to use multiple methods
    // 3. Logic is too complex (more than a few lines)

    public static void main(String[] args) {
        System.out.println("=== Lambda vs Anonymous ===\n");

        List<String> words = new ArrayList<>(List.of("apple", "pie", "banana", "a"));

        System.out.println("Before sort: " + words);

        LambdaVsAnonymous demo = new LambdaVsAnonymous();
        demo.sortWithLambda(words);

        System.out.println("After sort by length: " + words);

        System.out.println("\n--- Guidelines ---");
        System.out.println("✅ Use lambdas for functional interfaces");
        System.out.println("✅ Keep lambdas short (1-3 lines)");
        System.out.println("❌ Don't use if you need 'this'");
        System.out.println("❌ Don't use if logic is complex");
    }
}
