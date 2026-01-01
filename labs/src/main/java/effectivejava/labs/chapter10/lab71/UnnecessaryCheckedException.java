package effectivejava.labs.chapter10.lab71;

import java.util.*;

/**
 * ============================================================================
 * LAB 71: Avoid Unnecessary Use of Checked Exceptions (Item 71)
 * ============================================================================
 * Chapter 10, pp. 298-300
 * 
 * SCENARIO:
 * Checked exceptions are overused, forcing callers into verbose try-catch.
 * 
 * YOUR TASK:
 * TODO: Convert unnecessary checked exceptions to unchecked or Optional
 * ============================================================================
 */
public class UnnecessaryCheckedException {

    // =========================================================================
    // BAD: Checked exception when caller can't do anything useful
    // =========================================================================

    static class ConfigMissingException extends Exception {
        ConfigMissingException(String msg) { super(msg); }
    }

    static String getConfigBad(String key) throws ConfigMissingException {
        Map<String, String> config = Map.of("name", "app");
        String value = config.get(key);
        if (value == null) {
            throw new ConfigMissingException("Missing: " + key);
        }
        return value;
    }

    // Every caller must handle this:
    static void usageBad() {
        try {
            String name = getConfigBad("name");
            System.out.println(name);
        } catch (ConfigMissingException e) {
            // What can we really do here? Usually just wrap or rethrow
            throw new RuntimeException(e);
        }
    }

    // =========================================================================
    // GOOD: Use Optional for "might not exist"
    // =========================================================================

    static Optional<String> getConfigOptional(String key) {
        Map<String, String> config = Map.of("name", "app");
        return Optional.ofNullable(config.get(key));
    }

    static void usageOptional() {
        String name = getConfigOptional("name").orElse("default");
        System.out.println(name);
    }

    // =========================================================================
    // GOOD: State-testing method + unchecked exception
    // =========================================================================

    static class Config {
        private final Map<String, String> values = Map.of("name", "app");

        // State-testing method
        public boolean hasKey(String key) {
            return values.containsKey(key);
        }

        // Throws unchecked if called incorrectly
        public String get(String key) {
            String value = values.get(key);
            if (value == null) {
                throw new NoSuchElementException("Missing: " + key);
            }
            return value;
        }
    }

    static void usageStateTest() {
        Config config = new Config();
        if (config.hasKey("name")) {
            System.out.println(config.get("name"));
        } else {
            System.out.println("default");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Unnecessary Checked Exceptions ===\n");

        usageOptional();
        usageStateTest();

        System.out.println("\n--- When to avoid checked exceptions ---");
        System.out.println("1. Caller can't recover meaningfully");
        System.out.println("2. Condition can be tested beforehand");
        System.out.println("3. Only reasonable response is to rethrow");

        System.out.println("\n--- Alternatives ---");
        System.out.println("1. Optional<T> for 'might not exist'");
        System.out.println("2. State-testing method + unchecked exception");
        System.out.println("3. Return null/empty (if well-documented)");
    }
}
