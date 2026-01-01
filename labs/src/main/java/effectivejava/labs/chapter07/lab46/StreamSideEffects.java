package effectivejava.labs.chapter07.lab46;

import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.*;

/**
 * ============================================================================
 * LAB 46: Prefer Side-Effect-Free Functions in Streams (Item 46)
 * ============================================================================
 * Chapter 7, pp. 210-216
 * 
 * SCENARIO:
 * Stream operations modify external state (side effects). This breaks
 * the functional paradigm and can cause bugs, especially with parallel streams.
 * 
 * YOUR TASK:
 * TODO: Replace side-effecting operations with proper collectors
 * ============================================================================
 */
public class StreamSideEffects {

    // =========================================================================
    // BAD: forEach with side effects
    // =========================================================================
    
    public Map<String, Long> wordFrequencyBad(List<String> words) {
        Map<String, Long> freq = new HashMap<>();
        
        // WRONG: Modifying external state in forEach!
        words.stream()
             .forEach(word -> {
                 freq.merge(word.toLowerCase(), 1L, Long::sum);  // Side effect!
             });
        
        return freq;
    }

    // =========================================================================
    // GOOD: Use proper collector
    // =========================================================================
    
    public Map<String, Long> wordFrequencyGood(List<String> words) {
        return words.stream()
                .map(String::toLowerCase)
                .collect(groupingBy(word -> word, counting()));
        // No side effects - pure functional!
    }

    // =========================================================================
    // BAD: Accumulating in forEach
    // =========================================================================
    
    public List<String> filterAndCollectBad(List<String> items) {
        List<String> result = new ArrayList<>();
        
        items.stream()
             .filter(s -> s.length() > 3)
             .forEach(s -> result.add(s));  // Side effect!
        
        return result;
    }

    // =========================================================================
    // GOOD: Use toList()
    // =========================================================================
    
    public List<String> filterAndCollectGood(List<String> items) {
        return items.stream()
                .filter(s -> s.length() > 3)
                .toList();  // No side effects!
    }

    // =========================================================================
    // forEach is ONLY good for... actual side effects at the END
    // =========================================================================
    
    public void printResults(List<String> items) {
        // This is OK - forEach for its intended purpose (terminal action)
        items.stream()
             .map(String::toUpperCase)
             .forEach(System.out::println);
    }

    public static void main(String[] args) {
        System.out.println("=== Stream Side Effects ===\n");

        StreamSideEffects demo = new StreamSideEffects();

        List<String> words = List.of("Hello", "World", "hello", "Java", "java", "HELLO");

        System.out.println("Word frequency (good way):");
        System.out.println(demo.wordFrequencyGood(words));

        System.out.println("\n--- Rules ---");
        System.out.println("1. forEach is ONLY for terminal side effects");
        System.out.println("2. Use collectors instead of mutable accumulation");
        System.out.println("3. Intermediate operations should be pure functions");
        System.out.println("4. Side effects break parallel stream safety");
    }
}
