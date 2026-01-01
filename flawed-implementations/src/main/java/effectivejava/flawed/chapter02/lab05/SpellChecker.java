package effectivejava.flawed.chapter02.lab05;

import java.util.List;

/**
 * FLAWED IMPLEMENTATION - Hard-wired resource (utility class style)
 * 
 * This spell checker has a hard-coded dependency on EnglishDictionary.
 * Problems:
 * - Cannot swap for different languages
 * - Cannot inject mock for testing
 * - Cannot use domain-specific dictionaries
 * - Dependency is hidden, not explicit in interface
 */
public class SpellChecker {

    // PROBLEM: Hard-coded dependency - created internally!
    private static final EnglishDictionary DICTIONARY = new EnglishDictionary();

    // Private constructor - utility class
    private SpellChecker() {
        // Cannot be instantiated
    }

    /**
     * Check if a word is spelled correctly.
     * Uses the hard-coded English dictionary.
     */
    public static boolean isValid(String word) {
        // Always uses English dictionary - what about French? Medical terms?
        return DICTIONARY.contains(word.toLowerCase());
    }

    /**
     * Get suggestions for a misspelled word.
     */
    public static List<String> suggestions(String typo) {
        return DICTIONARY.suggestions(typo);
    }

    /**
     * Check a sentence and return list of misspelled words.
     */
    public static List<String> checkSentence(String sentence) {
        return java.util.Arrays.stream(sentence.split("\\s+"))
                .filter(word -> !isValid(word))
                .toList();
    }

    // How would you test this?
    // How would you make it work for French?
    // How would you add medical terminology without modifying this class?
}
