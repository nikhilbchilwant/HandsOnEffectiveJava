package effectivejava.labs.chapter02.lab05;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * ============================================================================
 * LAB 05: Prefer Dependency Injection to Hardwiring Resources (Item 5)
 * ============================================================================
 * Chapter 2, pp. 20-22
 * 
 * SCENARIO:
 * A SpellChecker that uses a dictionary. Currently it HARDWIRES an English
 * dictionary, making it impossible to test or use other dictionaries.
 * 
 * PROBLEMS:
 * 1. Can't test with a mock dictionary
 * 2. Can't use MedicalDictionary, LegalDictionary, etc.
 * 3. Violates Single Responsibility (creates its own dependency)
 * 
 * YOUR TASK:
 * TODO #1: Create a Dictionary interface
 * TODO #2: Make SpellChecker accept Dictionary via constructor injection
 * TODO #3: (Advanced) Accept Supplier<Dictionary> for lazy/fresh instances
 * ============================================================================
 */
public class SpellChecker {

    // =========================================================================
    // FIXME: Hardwired dependency! Can't change or mock!
    // =========================================================================
    
    private static final Set<String> DICTIONARY = loadDictionary();

    private static Set<String> loadDictionary() {
        // Hardcoded English dictionary
        return new HashSet<>(List.of(
                "hello", "world", "java", "effective", "programming",
                "spell", "check", "dictionary", "word", "language"
        ));
    }

    // FIXME: Static method can't use different dictionaries!
    public static boolean isValid(String word) {
        return DICTIONARY.contains(word.toLowerCase());
    }

    public static List<String> suggestions(String typo) {
        // Simplified: just check if any word starts with same letter
        return DICTIONARY.stream()
                .filter(w -> w.charAt(0) == typo.toLowerCase().charAt(0))
                .limit(3)
                .toList();
    }

    // =========================================================================
    // TODO: Refactor to use dependency injection
    // =========================================================================
    
    // Step 1: Create interface (in separate file or here):
    //
    // public interface Dictionary {
    //     boolean contains(String word);
    //     List<String> suggestions(String typo);
    // }

    // Step 2: Refactor SpellChecker:
    //
    // public class SpellChecker {
    //     private final Dictionary dictionary;
    //     
    //     public SpellChecker(Dictionary dictionary) {
    //         this.dictionary = Objects.requireNonNull(dictionary);
    //     }
    //     
    //     public boolean isValid(String word) {
    //         return dictionary.contains(word);
    //     }
    // }

    // Step 3 (Advanced): Supplier for lazy/fresh dictionaries:
    //
    // public SpellChecker(Supplier<? extends Dictionary> dictionaryFactory) {
    //     this.dictionary = dictionaryFactory.get();
    // }

    public static void main(String[] args) {
        System.out.println("=== Hardwired SpellChecker ===\n");

        System.out.println("isValid('hello'): " + isValid("hello"));
        System.out.println("isValid('hepatitis'): " + isValid("hepatitis"));

        System.out.println("\nPROBLEM: We can't check medical terms!");
        System.out.println("We need a MedicalDictionary, but SpellChecker");
        System.out.println("hardwires its own English dictionary.\n");

        System.out.println("SOLUTION: Inject the dictionary via constructor!");
        System.out.println("// SpellChecker checker = new SpellChecker(medicalDict);");
    }
}
