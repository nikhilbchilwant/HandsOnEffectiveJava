package effectivejava.labs.chapter04.lab24;

/**
 * ============================================================================
 * LAB 24: Favor Static Member Classes over Nonstatic (Item 24)
 * ============================================================================
 * Chapter 4, pp. 112-115
 * 
 * SCENARIO:
 * Nested classes are used without understanding the difference between
 * static and nonstatic. Nonstatic has hidden overhead!
 * 
 * YOUR TASK:
 * TODO: Make inner classes static when they don't need enclosing instance
 * ============================================================================
 */
public class StaticVsNonstaticInner {

    private String outerField = "outer";

    // =========================================================================
    // NONSTATIC member class - has reference to enclosing instance
    // =========================================================================

    class NonstaticInner {
        void printOuter() {
            // Can access outer instance implicitly
            System.out.println(outerField);
            System.out.println(StaticVsNonstaticInner.this.outerField);
        }
        // COST: Each instance holds hidden reference to outer object
        // - Extra memory (4 or 8 bytes per instance)
        // - Prevents garbage collection of outer object
        // - Can cause memory leaks!
    }

    // =========================================================================
    // STATIC member class - no reference to enclosing instance
    // =========================================================================

    static class StaticInner {
        void print() {
            System.out.println("I'm independent of any outer instance");
            // Can't access outerField - no enclosing instance!
            // System.out.println(outerField);  // Compile error
        }
        // No overhead, no memory leak risk
    }

    // =========================================================================
    // Common pattern: static helper class
    // =========================================================================

    // Like Map.Entry - doesn't need Map instance
    static class Entry<K, V> {
        private final K key;
        private final V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        K getKey() { return key; }
        V getValue() { return value; }
    }

    // =========================================================================
    // When to use nonstatic
    // =========================================================================

    // Adapter pattern - provides view of outer object
    class IteratorAdapter implements java.util.Iterator<String> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < 1;  // Just an example
        }

        @Override
        public String next() {
            index++;
            return outerField;  // Needs outer access!
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Static vs Nonstatic Inner Classes ===\n");

        // Nonstatic inner: need outer instance
        StaticVsNonstaticInner outer = new StaticVsNonstaticInner();
        NonstaticInner nonstatic = outer.new NonstaticInner();
        nonstatic.printOuter();

        // Static inner: independent
        StaticInner staticInner = new StaticInner();  // No outer needed
        staticInner.print();

        System.out.println("\n--- Guidelines ---");
        System.out.println("Use STATIC if:");
        System.out.println("  - Doesn't need access to enclosing instance");
        System.out.println("  - Could be extracted to top-level class");
        System.out.println("\nUse nonstatic only if:");
        System.out.println("  - Needs implicit access to enclosing instance");
        System.out.println("  - E.g., Iterator that iterates over outer's data");
    }
}
