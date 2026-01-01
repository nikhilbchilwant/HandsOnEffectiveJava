package effectivejava.labs.chapter08.lab55;

import java.util.*;

/**
 * ============================================================================
 * LAB 55: Return Optionals Judiciously (Item 55)
 * ============================================================================
 * Chapter 8, pp. 249-254
 * 
 * SCENARIO:
 * Code misuses Optional: as fields, in collections, or with primitives.
 * 
 * YOUR TASK:
 * TODO: Use Optional correctly (return type only, for genuinely optional values)
 * ============================================================================
 */
public class OptionalUsage {

    // =========================================================================
    // BAD: Optional as field
    // =========================================================================
    
    private Optional<String> name = Optional.empty();  // WRONG!
    // TODO: Use plain field + nullable, or sentinel value

    // =========================================================================
    // BAD: Optional in collection
    // =========================================================================
    
    private List<Optional<String>> items = new ArrayList<>();  // WRONG!
    // TODO: Just use List<String>, exclude nulls

    // =========================================================================
    // BAD: Optional for primitives
    // =========================================================================
    
    public Optional<Integer> findIndexBad(List<String> list, String target) {
        int idx = list.indexOf(target);
        return idx >= 0 ? Optional.of(idx) : Optional.empty();
        // WRONG: Use OptionalInt instead to avoid boxing!
    }

    // GOOD: Use OptionalInt for primitives
    public OptionalInt findIndexGood(List<String> list, String target) {
        int idx = list.indexOf(target);
        return idx >= 0 ? OptionalInt.of(idx) : OptionalInt.empty();
    }

    // =========================================================================
    // GOOD: Optional as return type for genuinely optional result
    // =========================================================================
    
    public Optional<String> findLongestWord(List<String> words) {
        if (words.isEmpty()) {
            return Optional.empty();  // Caller must handle absence
        }
        return words.stream()
                .max(Comparator.comparingInt(String::length));
    }

    // =========================================================================
    // BAD: Unwrapping without handling
    // =========================================================================
    
    public void badUnwrap(Optional<String> opt) {
        String value = opt.get();  // Throws if empty!
        // TODO: Use orElse, orElseGet, orElseThrow
    }

    public static void main(String[] args) {
        System.out.println("=== Optional Usage ===\n");

        OptionalUsage demo = new OptionalUsage();

        List<String> words = List.of("hello", "world", "programming");

        // GOOD usage
        Optional<String> longest = demo.findLongestWord(words);
        
        // Proper unwrapping:
        String result = longest.orElse("(none)");
        System.out.println("Longest: " + result);

        // Or with lambda for expensive default:
        result = longest.orElseGet(() -> computeDefault());
        System.out.println("With orElseGet: " + result);

        // Empty case
        Optional<String> empty = demo.findLongestWord(List.of());
        System.out.println("Empty case: " + empty.orElse("(none)"));

        System.out.println("\n--- Rules ---");
        System.out.println("✅ Optional for return types");
        System.out.println("❌ Optional as fields");
        System.out.println("❌ Optional in collections");
        System.out.println("❌ Optional<Integer> when OptionalInt works");
    }

    private static String computeDefault() {
        return "(computed)";
    }
}
