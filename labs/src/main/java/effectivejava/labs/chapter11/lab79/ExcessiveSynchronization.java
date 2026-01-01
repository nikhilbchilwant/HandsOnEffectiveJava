package effectivejava.labs.chapter11.lab79;

import java.util.*;

/**
 * ============================================================================
 * LAB 79: Avoid Excessive Synchronization (Item 79)
 * ============================================================================
 * Chapter 11, pp. 317-323
 * 
 * SCENARIO:
 * A synchronized method calls an "alien" method from within the lock.
 * This can cause deadlocks or data corruption!
 * 
 * YOUR TASK:
 * TODO: Move alien method calls outside the synchronized block
 * ============================================================================
 */
public class ExcessiveSynchronization {

    // =========================================================================
    // Observer pattern with synchronization problems
    // =========================================================================

    interface Observer<E> {
        void onAdded(E element);
    }

    static class ObservableSet<E> {
        private final Set<E> set = new HashSet<>();
        private final List<Observer<E>> observers = new ArrayList<>();

        public void addObserver(Observer<E> observer) {
            synchronized (observers) {
                observers.add(observer);
            }
        }

        public void add(E element) {
            boolean added;
            synchronized (set) {
                added = set.add(element);
            }
            
            if (added) {
                notifyObservers_BAD(element);  // DANGER!
            }
        }

        // =====================================================================
        // BAD: Calling alien method from within lock
        // =====================================================================
        
        private void notifyObservers_BAD(E element) {
            synchronized (observers) {
                for (Observer<E> observer : observers) {
                    observer.onAdded(element);  // ALIEN METHOD!
                    // Observer could:
                    // - Try to modify observers (ConcurrentModificationException)
                    // - Call add() (deadlock)
                    // - Block indefinitely
                }
            }
        }

        // =====================================================================
        // GOOD: Take a snapshot, call aliens outside lock
        // =====================================================================
        
        private void notifyObservers_GOOD(E element) {
            List<Observer<E>> snapshot;
            synchronized (observers) {
                snapshot = new ArrayList<>(observers);  // Snapshot inside lock
            }
            // Calls are now outside the lock
            for (Observer<E> observer : snapshot) {
                observer.onAdded(element);
            }
        }
    }

    // =========================================================================
    // Rule: Keep synchronized blocks SHORT
    // =========================================================================

    static class Counter {
        private int count;

        // BAD: Doing too much inside synchronized
        public synchronized void incrementAndLogBad() {
            count++;
            System.out.println("Count is now: " + count);  // IO in lock!
            // Also might do network calls, disk writes, etc.
        }

        // GOOD: Only protect the shared state
        public void incrementAndLogGood() {
            int localCount;
            synchronized (this) {
                localCount = ++count;  // Quick modification
            }
            // IO outside the lock
            System.out.println("Count is now: " + localCount);
        }

        public synchronized int getCount() { return count; }
    }

    public static void main(String[] args) {
        System.out.println("=== Avoid Excessive Synchronization ===\n");

        System.out.println("RULES:");
        System.out.println("1. Never call alien methods from synchronized blocks");
        System.out.println("2. Keep synchronized blocks SHORT");
        System.out.println("3. Do minimum work inside the lock");

        System.out.println("\nALIEN METHOD = code you don't control");
        System.out.println("- Observer callbacks");
        System.out.println("- Subclass method overrides");
        System.out.println("- Function arguments");

        System.out.println("\nSOLUTION: Open calls (snapshot + iterate outside lock)");
    }
}
