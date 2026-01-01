package effectivejava.labs.chapter04.lab21;

import java.util.*;

/**
 * ============================================================================
 * LAB 21: Design Interfaces for Posterity (Item 21)
 * ============================================================================
 * Chapter 4, pp. 104-107
 * 
 * SCENARIO:
 * Default methods can break implementations. Just because you CAN add
 * a default method doesn't mean it's safe!
 * 
 * YOUR TASK:
 * TODO: Understand risks of adding default methods to interfaces
 * ============================================================================
 */
public class InterfacesForPosterity {

    // =========================================================================
    // Example: Collection.removeIf caused real issues
    // =========================================================================

    interface MyCollection<E> {
        boolean add(E e);
        boolean remove(Object o);
        Iterator<E> iterator();

        // Java 8 added this default method to Collection  
        default boolean removeIf(java.util.function.Predicate<? super E> filter) {
            Objects.requireNonNull(filter);
            boolean removed = false;
            Iterator<E> each = iterator();
            while (each.hasNext()) {
                if (filter.test(each.next())) {
                    each.remove();
                    removed = true;
                }
            }
            return removed;
        }
        // Problem: SynchronizedCollection wrapped a collection
        // but this default method bypasses the synchronization!
        // Apache Commons SynchronizedCollection was broken by this.
    }

    // =========================================================================
    // Synchronized wrapper that breaks with default method
    // =========================================================================

    static class SynchronizedCollection<E> implements MyCollection<E> {
        private final MyCollection<E> c;
        private final Object mutex;

        SynchronizedCollection(MyCollection<E> c) {
            this.c = c;
            this.mutex = this;
        }

        @Override
        public synchronized boolean add(E e) {
            synchronized (mutex) { return c.add(e); }
        }

        @Override
        public synchronized boolean remove(Object o) {
            synchronized (mutex) { return c.remove(o); }
        }

        @Override
        public Iterator<E> iterator() {
            return c.iterator();  // Must be manually synchronized
        }

        // DANGER: Inherits default removeIf which is NOT synchronized!
        // removeIf() will call iterator() and remove() without locking!
    }

    public static void main(String[] args) {
        System.out.println("=== Interfaces for Posterity ===\n");

        System.out.println("Adding default methods to interfaces can:");
        System.out.println("1. Break existing implementations");
        System.out.println("2. Bypass synchronization (wrappers!)");
        System.out.println("3. Violate invariants of implementations");

        System.out.println("\nReal example: Collection.removeIf");
        System.out.println("- Added in Java 8 with a default implementation");
        System.out.println("- Broke SynchronizedCollection in Apache Commons");

        System.out.println("\n--- Guidelines ---");
        System.out.println("1. Think carefully before adding defaults");
        System.out.println("2. Test against known implementations");
        System.out.println("3. Existing implementations may need to override");
        System.out.println("4. Consider impact on wrapper classes");
    }
}
