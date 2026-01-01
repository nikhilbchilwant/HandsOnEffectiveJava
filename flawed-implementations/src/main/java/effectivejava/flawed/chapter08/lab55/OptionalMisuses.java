package effectivejava.flawed.chapter08.lab55;

import java.util.*;

/**
 * FLAWED IMPLEMENTATION - Various Optional misuses
 * 
 * This class demonstrates anti-patterns with Optional.
 */
public class OptionalMisuses {

    // ANTI-PATTERN 1: Optional as field
    // Wastes memory, complicates serialization, not intended use
    private Optional<String> cachedValue = Optional.empty();  // BAD!

    // ANTI-PATTERN 2: Optional in collection
    // Never use Optional in lists, maps, or arrays
    private List<Optional<String>> items = new ArrayList<>();  // BAD!

    // ANTI-PATTERN 3: Optional as parameter
    // Forces callers to create Optional, unclear when to pass empty
    public void processItem(Optional<String> item) {  // BAD!
        item.ifPresent(this::doProcess);
    }

    /**
     * ANTI-PATTERN 4: of() vs ofNullable() confusion
     * Optional.of() throws NullPointerException if null!
     */
    public Optional<String> findValue(String key) {
        String result = lookupValue(key);
        // WRONG: Will throw NPE if result is null!
        return Optional.of(result);  // Should be ofNullable()
    }

    /**
     * ANTI-PATTERN 5: isPresent() + get() instead of fluent methods
     */
    public String getValueOrDefault(Optional<String> opt) {
        // BAD: Verbose and error-prone
        if (opt.isPresent()) {
            return opt.get();
        } else {
            return "default";
        }
        // GOOD: return opt.orElse("default");
    }

    /**
     * ANTI-PATTERN 6: Optional for primitives
     * Use OptionalInt, OptionalLong, OptionalDouble instead
     */
    public Optional<Integer> findCount(String key) {
        int count = lookupCount(key);
        // BAD: Boxing overhead!
        return count >= 0 ? Optional.of(count) : Optional.empty();
        // BETTER: OptionalInt
    }

    /**
     * ANTI-PATTERN 7: Nesting Optionals
     */
    public Optional<Optional<String>> nested() {
        // This is almost never what you want
        return Optional.of(Optional.of("value"));  // BAD!
    }

    // Helper methods
    private String lookupValue(String key) { return null; }
    private int lookupCount(String key) { return -1; }
    private void doProcess(String s) { }
    
    public void setCachedValue(Optional<String> value) {
        this.cachedValue = value;
    }
    
    public Optional<String> getCachedValue() {
        return cachedValue;
    }
}
