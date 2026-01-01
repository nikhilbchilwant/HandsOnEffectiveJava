package effectivejava.labs.chapter07.lab45;

import java.util.*;
import java.util.stream.*;

/**
 * ============================================================================
 * LAB 45: Use Streams Judiciously (Item 45)
 * ============================================================================
 * Chapter 7, pp. 203-210
 * 
 * SCENARIO:
 * A developer converted everything to streams, even where loops are clearer.
 * Stream pipelines can be elegant but overuse hurts readability.
 * 
 * YOUR TASK:
 * TODO #1: Identify which uses are good (keep)
 * TODO #2: Identify which uses are bad (revert to loops)
 * TODO #3: Apply guideline: favor streams for filtering, mapping, grouping
 * ============================================================================
 */
public class StreamVsLoop {

    // =========================================================================
    // GOOD USE: Grouping is stream-natural
    // =========================================================================
    
    public static Map<Integer, List<String>> groupByLength(List<String> words) {
        return words.stream()
                .collect(Collectors.groupingBy(String::length));
    }

    // =========================================================================
    // BAD USE: Cartesian product is clearer with loops
    // =========================================================================
    
    // Confusing nested flatMap!
    public static List<int[]> cartesianProductStream(List<Integer> a, List<Integer> b) {
        return a.stream()
                .flatMap(x -> b.stream()
                        .map(y -> new int[]{x, y}))
                .toList();
    }

    // TODO: Revert to clear loop version:
    // public static List<int[]> cartesianProductLoop(List<Integer> a, List<Integer> b) {
    //     List<int[]> result = new ArrayList<>();
    //     for (int x : a) {
    //         for (int y : b) {
    //             result.add(new int[]{x, y});
    //         }
    //     }
    //     return result;
    // }

    // =========================================================================
    // GOOD USE: Filtering and counting
    // =========================================================================
    
    public static long countWordsStartingWith(List<String> words, char c) {
        return words.stream()
                .filter(w -> !w.isEmpty() && w.charAt(0) == c)
                .count();
    }

    // =========================================================================
    // QUESTIONABLE: Too many operations
    // =========================================================================
    
    public static List<String> processWords(List<String> words) {
        return words.stream()
                .filter(w -> w.length() > 3)
                .map(String::toLowerCase)
                .map(w -> w.replaceAll("[^a-z]", ""))
                .filter(w -> !w.isEmpty())
                .distinct()
                .sorted()
                .limit(10)
                .toList();
        // 7 operations! Consider splitting or using loops for clarity.
    }

    public static void main(String[] args) {
        System.out.println("=== Streams Judiciously ===\n");

        List<String> words = List.of("hello", "world", "stream", "loop", "java");

        System.out.println("GOOD: groupByLength");
        System.out.println(groupByLength(words));

        System.out.println("\nGOOD: countWordsStartingWith('h')");
        System.out.println(countWordsStartingWith(words, 'h'));

        System.out.println("\nQUESTIONABLE: Cartesian product");
        var product = cartesianProductStream(List.of(1, 2), List.of(3, 4));
        product.forEach(arr -> System.out.println(Arrays.toString(arr)));

        System.out.println("\n--- Guidelines ---");
        System.out.println("✅ Streams: filter, map, collect, group");
        System.out.println("❌ Loops: complex logic, early exit, mutable state");
        System.out.println("⚠️ Review: 5+ stream operations");
    }
}
