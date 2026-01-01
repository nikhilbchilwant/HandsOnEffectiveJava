package effectivejava.labs.chapter05.lab30;

import java.util.*;
import java.util.function.*;

/**
 * ============================================================================
 * LAB 30: Favor Generic Methods (Item 30)
 * ============================================================================
 * Chapter 5, pp. 135-139
 * 
 * SCENARIO:
 * Static utility methods using raw types. These should be generic!
 * 
 * YOUR TASK:
 * TODO: Add type parameters to make these methods generic
 * ============================================================================
 */
public class GenericMethods {

    // =========================================================================
    // BAD: Raw types - requires casts, not type-safe
    // =========================================================================

    public static Set union_bad(Set s1, Set s2) {
        Set result = new HashSet(s1);
        result.addAll(s2);
        return result;  // Caller must cast!
    }

    // =========================================================================
    // GOOD: Generic method - type-safe!
    // =========================================================================

    public static <E> Set<E> union(Set<E> s1, Set<E> s2) {
        Set<E> result = new HashSet<>(s1);
        result.addAll(s2);
        return result;
    }

    // =========================================================================
    // Generic singleton factory pattern
    // =========================================================================

    // One instance works for all types due to erasure
    private static final UnaryOperator<Object> IDENTITY = t -> t;

    @SuppressWarnings("unchecked")
    public static <T> UnaryOperator<T> identityFunction() {
        return (UnaryOperator<T>) IDENTITY;  // Safe due to erasure
    }

    // =========================================================================
    // Recursive type bound for Comparable
    // =========================================================================

    // <E extends Comparable<E>> means E can be compared to other Es
    public static <E extends Comparable<E>> E max(Collection<E> c) {
        if (c.isEmpty()) {
            throw new IllegalArgumentException("Empty collection");
        }

        E result = null;
        for (E e : c) {
            if (result == null || e.compareTo(result) > 0) {
                result = e;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("=== Generic Methods ===\n");

        Set<String> guys = Set.of("Tom", "Dick", "Harry");
        Set<String> stooges = Set.of("Larry", "Moe", "Curly");

        // Type-safe union
        Set<String> all = union(guys, stooges);
        System.out.println("Union: " + all);

        // Generic singleton factory
        UnaryOperator<String> sameString = identityFunction();
        UnaryOperator<Number> sameNumber = identityFunction();
        System.out.println("Identity: " + sameString.apply("hello"));

        // Recursive type bound
        List<Integer> nums = List.of(1, 5, 3, 9, 2);
        System.out.println("Max: " + max(nums));

        System.out.println("\n--- Syntax ---");
        System.out.println("public static <E> Set<E> union(Set<E> s1, Set<E> s2)");
        System.out.println("              ^^^                                   ");
        System.out.println("           Type parameter declaration");
    }
}
