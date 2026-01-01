package effectivejava.flawed.chapter02.lab05;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A simple English dictionary used by the flawed SpellChecker.
 */
public class EnglishDictionary {

    private final Set<String> words;

    public EnglishDictionary() {
        // In real world, would load from file
        this.words = Set.of(
                "the", "be", "to", "of", "and", "a", "in", "that", "have", "i",
                "it", "for", "not", "on", "with", "he", "as", "you", "do", "at",
                "this", "but", "his", "by", "from", "they", "we", "say", "her",
                "she", "or", "an", "will", "my", "one", "all", "would", "there",
                "their", "what", "so", "up", "out", "if", "about", "who", "get",
                "which", "go", "me", "when", "make", "can", "like", "time",
                "no", "just", "him", "know", "take", "people", "into", "year",
                "your", "good", "some", "could", "them", "see", "other", "than",
                "then", "now", "look", "only", "come", "its", "over", "think",
                "also", "back", "after", "use", "two", "how", "our", "work",
                "first", "well", "way", "even", "new", "want", "because", "any",
                "these", "give", "day", "most", "us", "hello", "world", "java",
                "code", "test", "spell", "check", "dictionary", "word", "language"
        );
    }

    public boolean contains(String word) {
        return words.contains(word.toLowerCase());
    }

    public List<String> suggestions(String misspelled) {
        List<String> result = new ArrayList<>();
        String lower = misspelled.toLowerCase();

        // Simple suggestion: find words that start with same letters
        for (String word : words) {
            if (word.startsWith(lower.substring(0, Math.min(2, lower.length())))) {
                result.add(word);
                if (result.size() >= 5) break;
            }
        }
        return result;
    }
}
