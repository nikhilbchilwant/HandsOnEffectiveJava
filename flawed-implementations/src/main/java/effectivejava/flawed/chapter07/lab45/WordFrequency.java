package effectivejava.flawed.chapter07.lab45;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FLAWED IMPLEMENTATION - Imperative where stream would be better
 * 
 * This implementation uses loops where streams would be cleaner.
 * Word frequency counting is a natural fit for streams.
 */
public class WordFrequency {

    /**
     * This imperative version is fine but streams would be cleaner.
     * Counting and grouping are stream-natural operations.
     */
    public static Map<String, Long> countWordsImperative(List<String> words) {
        Map<String, Long> frequency = new HashMap<>();
        
        for (String word : words) {
            String lower = word.toLowerCase();
            Long count = frequency.get(lower);
            if (count == null) {
                frequency.put(lower, 1L);
            } else {
                frequency.put(lower, count + 1);
            }
        }
        
        return frequency;
    }

    /**
     * Find the top N most frequent words - imperative version.
     * Stream version would be more elegant.
     */
    public static List<Map.Entry<String, Long>> topNImperative(
            Map<String, Long> frequency, int n) {
        List<Map.Entry<String, Long>> entries = new ArrayList<>(frequency.entrySet());
        
        // Sort by frequency descending
        entries.sort((a, b) -> Long.compare(b.getValue(), a.getValue()));
        
        // Take first N
        List<Map.Entry<String, Long>> result = new ArrayList<>();
        for (int i = 0; i < Math.min(n, entries.size()); i++) {
            result.add(entries.get(i));
        }
        
        return result;
    }

    /**
     * Group words by their length - imperative version.
     * Collectors.groupingBy would be cleaner.
     */
    public static Map<Integer, List<String>> groupByLengthImperative(List<String> words) {
        Map<Integer, List<String>> result = new HashMap<>();
        
        for (String word : words) {
            int length = word.length();
            List<String> group = result.get(length);
            if (group == null) {
                group = new ArrayList<>();
                result.put(length, group);
            }
            group.add(word);
        }
        
        return result;
    }

    // TODO: Refactor these to use streams:
    // - Collectors.groupingBy for grouping
    // - Collectors.counting() for frequency
    // - sorted() and limit() for top N

    public static void main(String[] args) {
        List<String> words = List.of(
            "the", "quick", "brown", "fox", "jumps", "over", "the", "lazy",
            "dog", "the", "fox", "was", "quick", "and", "the", "dog", "was", "lazy"
        );

        System.out.println("Word frequency:");
        Map<String, Long> freq = countWordsImperative(words);
        freq.forEach((word, count) -> System.out.printf("  %s: %d%n", word, count));

        System.out.println("\nTop 3 words:");
        topNImperative(freq, 3).forEach(e -> 
            System.out.printf("  %s: %d%n", e.getKey(), e.getValue()));

        System.out.println("\nWords by length:");
        groupByLengthImperative(words).forEach((len, wordList) ->
            System.out.printf("  %d: %s%n", len, wordList));
    }
}
