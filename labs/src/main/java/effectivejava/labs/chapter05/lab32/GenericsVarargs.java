package effectivejava.labs.chapter05.lab32;

import java.util.*;

/**
 * ============================================================================
 * LAB 32: Combine Generics and Varargs Judiciously (Item 32)
 * ============================================================================
 * Chapter 5, pp. 147-151
 * 
 * SCENARIO:
 * Varargs with generics creates unchecked warnings and potential heap pollution.
 * 
 * YOUR TASK:
 * TODO: Use @SafeVarargs correctly when the method is safe
 * ============================================================================
 */
public class GenericsVarargs {

    // =========================================================================
    // DANGEROUS: Generic varargs can corrupt the heap
    // =========================================================================

    // This is DANGEROUS!
    static void dangerous(List<String>... lists) {
        Object[] array = lists;  // Legal! Varargs is array
        array[0] = List.of(42);  // Heap pollution!
        String s = lists[0].get(0);  // ClassCastException!
    }

    // =========================================================================
    // SAFE: Don't store anything in the varargs array
    // =========================================================================

    // This is SAFE - only reads from the array
    @SafeVarargs  // Promise to compiler that we're safe
    static <T> List<T> flatten(List<? extends T>... lists) {
        List<T> result = new ArrayList<>();
        for (List<? extends T> list : lists) {
            result.addAll(list);
        }
        return result;
    }

    // =========================================================================
    // SAFE: Can expose the varargs array if it's typed Object[]
    // =========================================================================

    @SafeVarargs
    static <T> T[] toArray(T... args) {
        // ACTUALLY UNSAFE! Don't do this!
        // The returned array has wrong runtime type
        return args;
    }

    // Better: Accept array explicitly
    static <T> T[] toArraySafe(T[] array, T... args) {
        System.arraycopy(args, 0, array, 0, args.length);
        return array;
    }

    // =========================================================================
    // Rules for @SafeVarargs
    // =========================================================================

    // Safe if method:
    // 1. Doesn't store anything into the varargs array
    // 2. Doesn't expose the varargs array (or a clone) to untrusted code

    @SafeVarargs  // Safe!
    static <T> void printAll(T... items) {
        for (T item : items) {
            System.out.println(item);
        }
    }

    // =========================================================================
    // Alternative: Use List instead of varargs
    // =========================================================================

    // No warnings, no problems!
    static <T> List<T> flattenList(List<List<T>> lists) {
        List<T> result = new ArrayList<>();
        for (List<T> list : lists) {
            result.addAll(list);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("=== Generics + Varargs ===\n");

        // Using safe method
        List<String> flat = flatten(
            List.of("a", "b"),
            List.of("c", "d"));
        System.out.println("Flattened: " + flat);

        // Using list alternative
        List<String> flat2 = flattenList(List.of(
            List.of("a", "b"),
            List.of("c", "d")));
        System.out.println("Flattened (list): " + flat2);

        System.out.println("\n--- @SafeVarargs Rules ---");
        System.out.println("Use on method if:");
        System.out.println("1. Doesn't store into varargs array");
        System.out.println("2. Doesn't expose the varargs array");
        System.out.println("\nMust be on static/final/private methods");
    }
}
