package effectivejava.labs.chapter07.lab47;

import java.util.*;
import java.util.stream.*;

/**
 * ============================================================================
 * LAB 47: Prefer Collection to Stream as Return Type (Item 47)
 * ============================================================================
 * Chapter 7, pp. 216-222
 * 
 * SCENARIO:
 * API returns Stream when Collection would be more versatile.
 * 
 * YOUR TASK:
 * TODO: Understand when to return Collection vs Stream
 * ============================================================================
 */
public class CollectionVsStream {

    // =========================================================================
    // PROBLEM: Returning Stream limits what caller can do
    // =========================================================================

    public static Stream<String> getWordsAsStream() {
        return Stream.of("hello", "world", "java");
        // Caller can only iterate once!
        // Can't easily get size, check contains, etc.
    }

    // =========================================================================
    // BETTER: Return Collection when feasible
    // =========================================================================

    public static Collection<String> getWordsAsCollection() {
        return List.of("hello", "world", "java");
        // Caller can:
        // - Iterate multiple times
        // - Get size
        // - Check contains
        // - Stream if needed: collection.stream()
    }

    // =========================================================================
    // When Stream IS appropriate
    // =========================================================================

    // 1. Sequence is infinite or very large
    public static Stream<Integer> infiniteNumbers() {
        return Stream.iterate(0, n -> n + 1);
    }

    // 2. Elements are computed on demand
    public static Stream<String> generateLines(int count) {
        return Stream.generate(() -> "Line " + Math.random())
                     .limit(count);
    }

    // =========================================================================
    // Custom Collection for subsequences (from the book)
    // =========================================================================

    // Returns power set (all subsets) as a custom AbstractList
    // More efficient than materializing all subsets!
    public static <E> Collection<Set<E>> powerSet(Set<E> s) {
        List<E> src = new ArrayList<>(s);
        if (src.size() > 30) {
            throw new IllegalArgumentException("Set too big: " + s);
        }
        
        return new AbstractList<Set<E>>() {
            @Override
            public int size() {
                return 1 << src.size();  // 2^n
            }

            @Override
            public boolean contains(Object o) {
                return o instanceof Set && src.containsAll((Set<?>) o);
            }

            @Override
            public Set<E> get(int index) {
                Set<E> result = new HashSet<>();
                for (int i = 0; index != 0; i++, index >>= 1) {
                    if ((index & 1) == 1) {
                        result.add(src.get(i));
                    }
                }
                return result;
            }
        };
    }

    public static void main(String[] args) {
        System.out.println("=== Collection vs Stream ===\n");

        // Collection is versatile
        Collection<String> coll = getWordsAsCollection();
        System.out.println("Size: " + coll.size());  // Easy!
        System.out.println("Contains 'java': " + coll.contains("java"));
        
        // Can still stream if needed
        coll.stream().map(String::toUpperCase).forEach(System.out::println);

        // Power set example
        Set<String> input = Set.of("a", "b", "c");
        System.out.println("\nPower set of " + input + ":");
        powerSet(input).forEach(System.out::println);

        System.out.println("\n--- Guidelines ---");
        System.out.println("Return Collection if:");
        System.out.println("  - Can fit in memory");
        System.out.println("  - Caller might iterate multiple times");
        System.out.println("  - Size/contains useful");
        System.out.println("\nReturn Stream if:");
        System.out.println("  - Infinite or huge");
        System.out.println("  - Computed on demand");
    }
}
