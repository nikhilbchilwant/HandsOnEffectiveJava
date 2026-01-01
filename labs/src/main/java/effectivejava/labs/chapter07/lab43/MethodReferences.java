package effectivejava.labs.chapter07.lab43;

import java.util.*;
import java.util.function.*;

/**
 * ============================================================================
 * LAB 43: Prefer Method References to Lambdas (Item 43)
 * ============================================================================
 * Chapter 7, pp. 197-199
 * 
 * SCENARIO:
 * Lambdas are used where method references would be more concise.
 * 
 * YOUR TASK:
 * TODO: Replace lambdas with method references where clearer
 * ============================================================================
 */
public class MethodReferences {

    // =========================================================================
    // Method reference types
    // =========================================================================

    public static void main(String[] args) {
        System.out.println("=== Method References ===\n");

        List<String> words = new ArrayList<>(List.of("hello", "world", "java"));

        // -----------------------------------------------------------------
        // 1. Static method reference
        // -----------------------------------------------------------------
        
        // Lambda
        words.forEach(s -> System.out.println(s));
        
        // Method reference (better)
        words.forEach(System.out::println);

        // -----------------------------------------------------------------
        // 2. Bound instance method reference
        // -----------------------------------------------------------------
        
        String prefix = "PREFIX: ";
        
        // Lambda
        words.stream().map(s -> prefix.concat(s)).forEach(System.out::println);
        
        // Method reference
        words.stream().map(prefix::concat).forEach(System.out::println);

        // -----------------------------------------------------------------
        // 3. Unbound instance method reference
        // -----------------------------------------------------------------
        
        // Lambda
        words.sort((s1, s2) -> s1.compareToIgnoreCase(s2));
        
        // Method reference (cleaner)
        words.sort(String::compareToIgnoreCase);

        // -----------------------------------------------------------------
        // 4. Constructor reference
        // -----------------------------------------------------------------
        
        // Lambda
        Supplier<List<String>> listFactory1 = () -> new ArrayList<>();
        
        // Method reference
        Supplier<List<String>> listFactory2 = ArrayList::new;

        // -----------------------------------------------------------------
        // When lambda is BETTER than method reference
        // -----------------------------------------------------------------
        
        // Method reference can be longer!
        // service.execute(GoshThisClassNameIsHumongous::action);
        // vs
        // service.execute(() -> action());

        System.out.println("\n--- Types ---");
        System.out.println("Static:      Integer::parseInt");
        System.out.println("Bound:       instant::isAfter");
        System.out.println("Unbound:     String::toLowerCase");
        System.out.println("Constructor: TreeMap<K,V>::new");
        System.out.println("Array:       int[]::new");
    }
}
