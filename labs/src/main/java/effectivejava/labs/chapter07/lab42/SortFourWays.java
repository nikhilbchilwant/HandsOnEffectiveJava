package effectivejava.labs.chapter07.lab42;

import java.util.*;
import static java.util.Comparator.comparingInt;

/**
 * ============================================================================
 * REFERENCE: SortFourWays - Bloch's Lambda Evolution Example
 * ============================================================================
 * From "Effective Java" 3rd Edition, Pages 193-194
 * 
 * Shows the evolution from anonymous classes to lambdas to method references.
 * ============================================================================
 */
public class SortFourWays {

    public static void main(String[] args) {
        List<String> words = new ArrayList<>(List.of("hello", "world", "a", "programming", "is", "fun"));

        System.out.println("=== Evolution of Function Objects ===\n");
        System.out.println("Original: " + words);

        // =====================================================================
        // WAY 1: Anonymous class - OBSOLETE!
        // =====================================================================
        Collections.sort(words, new Comparator<String>() {
            public int compare(String s1, String s2) {
                return Integer.compare(s1.length(), s2.length());
            }
        });
        System.out.println("\n1. Anonymous class (obsolete):");
        System.out.println("   " + words);
        Collections.shuffle(words);

        // =====================================================================
        // WAY 2: Lambda expression
        // =====================================================================
        Collections.sort(words,
                (s1, s2) -> Integer.compare(s1.length(), s2.length()));
        System.out.println("\n2. Lambda expression:");
        System.out.println("   " + words);
        Collections.shuffle(words);

        // =====================================================================
        // WAY 3: Comparator factory + method reference
        // =====================================================================
        Collections.sort(words, comparingInt(String::length));
        System.out.println("\n3. Comparator.comparingInt + method reference:");
        System.out.println("   " + words);
        Collections.shuffle(words);

        // =====================================================================
        // WAY 4: List.sort with comparator (BEST)
        // =====================================================================
        words.sort(comparingInt(String::length));
        System.out.println("\n4. List.sort (cleanest):");
        System.out.println("   " + words);

        System.out.println("\n--- Evolution ---");
        System.out.println("Anonymous class → Lambda → Method reference");
        System.out.println("Each step is more concise and readable!");
    }
}
