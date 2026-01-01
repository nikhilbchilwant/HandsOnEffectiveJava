package effectivejava.labs.chapter11.lab82;

import java.util.concurrent.*;

/**
 * ============================================================================
 * LAB 82: Document Thread Safety (Item 82)
 * ============================================================================
 * Chapter 11, pp. 330-333
 * 
 * SCENARIO:
 * Classes don't document their thread safety guarantees.
 * Users don't know if they need external synchronization.
 * 
 * YOUR TASK:
 * TODO: Document thread safety level for each class
 * ============================================================================
 */
public class ThreadSafetyDocumentation {

    // =========================================================================
    // Thread Safety Levels (document which one!)
    // =========================================================================

    /**
     * IMMUTABLE - instances are constant, no synchronization needed.
     * Examples: String, Long, BigInteger
     */
    // @Immutable  // Use JSR-305 annotation
    final class ImmutableClass {
        private final String value;
        public ImmutableClass(String value) { this.value = value; }
        public String getValue() { return value; }
    }

    /**
     * UNCONDITIONALLY THREAD-SAFE - mutable but handles its own sync.
     * Examples: AtomicLong, ConcurrentHashMap
     */
    // @ThreadSafe
    class UnconditionallyThreadSafe {
        private final Object lock = new Object();
        private int value;

        public void increment() {
            synchronized (lock) {
                value++;
            }
        }
    }

    /**
     * CONDITIONALLY THREAD-SAFE - some methods need external sync.
     * Example: Collections.synchronizedList (iteration needs sync)
     * 
     * MUST document which methods need external sync and which lock!
     */
    // @ThreadSafe(comment = "Iteration requires external synchronization")
    class ConditionallyThreadSafe {
        private final java.util.List<String> list = 
            java.util.Collections.synchronizedList(new java.util.ArrayList<>());



        // Thread-safe:
        public void add(String s) { list.add(s); }

        // REQUIRES EXTERNAL SYNC - document this!
        /**
         * Iterates over all elements.
         * <p>Synchronization: Caller must synchronize on this object:
         * <pre>{@code
         *   synchronized (instance.getList()) {
         *       for (String s : instance.getList()) { ... }
         *   }
         * }</pre>
         */
        public java.util.List<String> getList() { return list; }
    }

    /**
     * NOT THREAD-SAFE - requires external synchronization.
     * Examples: ArrayList, HashMap
     */
    // @NotThreadSafe
    class NotThreadSafe {
        private int value;
        public void increment() { value++; }  // Not atomic!
    }

    /**
     * THREAD-HOSTILE - unsafe even with external sync.
     * Rare - usually a bug.
     */

    // =========================================================================
    // Lock documentation
    // =========================================================================

    /**
     * Example with documented lock.
     * 
     * Thread safety: This class is conditionally thread-safe.
     * Lock: Callers must synchronize on 'this' for compound actions.
     */
    class DocumentedLock {
        private int count;

        // Individual method is safe
        public synchronized int getCount() { return count; }
        public synchronized void setCount(int c) { count = c; }

        // But compound action needs external sync!
        // synchronized (obj) { obj.setCount(obj.getCount() + 1); }
    }

    public static void main(String[] args) {
        System.out.println("=== Thread Safety Documentation ===\n");

        System.out.println("Thread Safety Levels:");
        System.out.println("1. IMMUTABLE - constant, no sync needed");
        System.out.println("2. UNCONDITIONALLY THREAD-SAFE - handles own sync");
        System.out.println("3. CONDITIONALLY THREAD-SAFE - some ops need ext sync");
        System.out.println("4. NOT THREAD-SAFE - caller must sync");
        System.out.println("5. THREAD-HOSTILE - broken");

        System.out.println("\nDocumentation checklist:");
        System.out.println("□ State thread safety level");
        System.out.println("□ For conditional: which methods need sync");
        System.out.println("□ Which lock to use");
        System.out.println("□ Consider @ThreadSafe, @NotThreadSafe annotations");
    }
}
