package effectivejava.labs.chapter09.lab64;

import java.util.*;

/**
 * ============================================================================
 * LAB 64: Refer to Objects by Their Interfaces (Item 64)
 * ============================================================================
 * Chapter 9, pp. 280-281
 * 
 * SCENARIO:
 * Code uses concrete types when interfaces would provide more flexibility.
 * 
 * YOUR TASK:
 * TODO: Replace concrete type declarations with interfaces
 * ============================================================================
 */
public class InterfaceReferences {

    // =========================================================================
    // BAD: Concrete type reference
    // =========================================================================

    public void badExample() {
        // WRONG: Tied to LinkedHashSet implementation
        LinkedHashSet<String> items = new LinkedHashSet<>();
        items.add("a");
        processSetBad(items);
    }

    // Also bad: parameter type is too specific
    void processSetBad(LinkedHashSet<String> set) {
        // Can only process LinkedHashSet!
    }

    // =========================================================================
    // GOOD: Interface reference
    // =========================================================================

    public void goodExample() {
        // RIGHT: Declaration uses Set interface
        Set<String> items = new LinkedHashSet<>();
        items.add("a");

        // Easy to switch implementation!
        items = new HashSet<>(items);  // No other code changes needed
        items = new TreeSet<>(items);  // Still works!

        processSetGood(items);
    }

    // Good: parameter accepts any Set
    void processSetGood(Set<String> set) {
        // Works with ANY Set implementation
    }

    // =========================================================================
    // When to use concrete type
    // =========================================================================

    public void whenConcrete() {
        // 1. When you need implementation-specific methods
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        String first = map.firstKey();  // TreeMap-specific!

        // 2. When there's no appropriate interface
        Random random = new Random();  // No common interface

        // 3. When class provides value beyond interface
        String s = "hello";  // String, not CharSequence (usually)
    }

    public static void main(String[] args) {
        System.out.println("=== Interface References ===\n");

        System.out.println("BAD:");
        System.out.println("  LinkedHashSet<String> set = new LinkedHashSet<>();");
        System.out.println("");
        System.out.println("GOOD:");
        System.out.println("  Set<String> set = new LinkedHashSet<>();");

        System.out.println("\nBenefits:");
        System.out.println("1. Easy to change implementation");
        System.out.println("2. More flexible APIs");
        System.out.println("3. Documents intent (I need a Set, not LinkedHashSet features)");

        System.out.println("\nUse concrete when:");
        System.out.println("- Need implementation-specific methods");
        System.out.println("- No suitable interface exists");
    }
}
