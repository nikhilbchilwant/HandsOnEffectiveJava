package effectivejava.labs.chapter08.lab52;

import java.util.*;

/**
 * ============================================================================
 * LAB 52: Use Overloading Judiciously (Item 52)
 * ============================================================================
 * Chapter 8, pp. 238-245
 * 
 * SCENARIO:
 * Overloaded methods are confusing because selection is at COMPILE time,
 * not runtime. This leads to surprises!
 * 
 * YOUR TASK:
 * TODO: Understand the problem and use different method names instead
 * ============================================================================
 */
public class OverloadingPitfall {

    // =========================================================================
    // CONFUSING: Overloaded methods - resolved at compile time!
    // =========================================================================

    public static String classify(Set<?> s) {
        return "Set";
    }

    public static String classify(List<?> list) {
        return "List";
    }

    public static String classify(Collection<?> c) {
        return "Unknown Collection";
    }

    // =========================================================================
    // What you might EXPECT (but won't get!)
    // =========================================================================

    public static void demonstrateProblem() {
        Collection<?>[] collections = {
            new HashSet<String>(),
            new ArrayList<String>(),
            new HashMap<String, String>().values()
        };

        // You might expect: Set, List, Unknown Collection
        // But you get: Unknown Collection, Unknown Collection, Unknown Collection!
        for (Collection<?> c : collections) {
            System.out.println(classify(c));  // Compile-time type is Collection!
        }
    }

    // =========================================================================
    // CONTRAST: Overriding IS resolved at runtime (polymorphism)
    // =========================================================================

    static class Wine {
        String name() { return "wine"; }
    }

    static class SparklingWine extends Wine {
        @Override String name() { return "sparkling wine"; }
    }

    static class Champagne extends SparklingWine {
        @Override String name() { return "champagne"; }
    }

    public static void demonstrateOverriding() {
        List<Wine> wines = List.of(new Wine(), new SparklingWine(), new Champagne());

        // This DOES work as expected - runtime dispatch!
        for (Wine wine : wines) {
            System.out.println(wine.name());  // wine, sparkling wine, champagne
        }
    }

    // =========================================================================
    // SOLUTION: Use instanceof, not overloading
    // =========================================================================

    public static String classifyCorrect(Collection<?> c) {
        return c instanceof Set ? "Set" :
               c instanceof List ? "List" : "Unknown Collection";
    }

    public static void main(String[] args) {
        System.out.println("=== Overloading Pitfall ===\n");

        System.out.println("Overloaded classify (WRONG):");
        demonstrateProblem();

        System.out.println("\nOverridden name (WORKS):");
        demonstrateOverriding();

        System.out.println("\nUsing instanceof (CORRECT):");
        Collection<?>[] collections = {
            new HashSet<String>(), new ArrayList<String>()
        };
        for (Collection<?> c : collections) {
            System.out.println(classifyCorrect(c));
        }

        System.out.println("\n--- Key Point ---");
        System.out.println("Overloading: compile-time selection (confusing)");
        System.out.println("Overriding: runtime selection (expected)");
        System.out.println("Prefer different method names to overloading");
    }
}
