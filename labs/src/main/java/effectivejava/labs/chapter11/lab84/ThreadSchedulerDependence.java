package effectivejava.labs.chapter11.lab84;

/**
 * ============================================================================
 * LAB 84: Don't Depend on the Thread Scheduler (Item 84)
 * ============================================================================
 * Chapter 11, pp. 336-338
 * 
 * SCENARIO:
 * Program correctness depends on thread scheduling, using busy-waits.
 * This is fragile and non-portable.
 * 
 * YOUR TASK:
 * TODO: Replace scheduler-dependent code with proper synchronization
 * ============================================================================
 */
public class ThreadSchedulerDependence {

    // =========================================================================
    // BAD: Busy-wait - wastes CPU and depends on scheduler
    // =========================================================================

    static volatile boolean ready = false;

    static void busyWaitBad() {
        // DON'T DO THIS!
        while (!ready) {
            // Busy-wait: burns CPU, may never yield!
        }
        System.out.println("Ready!");
    }

    // =========================================================================
    // BAD: Depending on Thread.yield()
    // =========================================================================

    static void yieldHackBad() {
        while (!ready) {
            Thread.yield();  // "Please let other threads run"
            // But JVM can ignore this! Behavior varies by platform.
        }
    }

    // =========================================================================
    // GOOD: Use proper synchronization
    // =========================================================================

    private static final Object lock = new Object();
    private static boolean readyGood = false;

    static void waitProperlyGood() throws InterruptedException {
        synchronized (lock) {
            while (!readyGood) {
                lock.wait();  // Releases lock, waits efficiently
            }
        }
        System.out.println("Ready!");
    }

    static void signalGood() {
        synchronized (lock) {
            readyGood = true;
            lock.notifyAll();
        }
    }

    // =========================================================================
    // EVEN BETTER: Higher-level concurrency utilities
    // =========================================================================

    static java.util.concurrent.CountDownLatch latch = 
        new java.util.concurrent.CountDownLatch(1);

    static void waitBestPractice() throws InterruptedException {
        latch.await();  // Clean, efficient, no busy-wait
        System.out.println("Ready!");
    }

    public static void main(String[] args) {
        System.out.println("=== Thread Scheduler Dependence ===\n");

        System.out.println("DON'T depend on scheduler behavior!");

        System.out.println("\nBAD practices:");
        System.out.println("- Busy-wait loops");
        System.out.println("- Thread.yield() to fix races");
        System.out.println("- Thread.sleep() for synchronization");

        System.out.println("\nGOOD practices:");
        System.out.println("- Proper synchronization (wait/notify)");
        System.out.println("- CountDownLatch, Semaphore, etc.");
        System.out.println("- Design for thread count = 1 to n");

        System.out.println("\nIf program relies on scheduler, it's WRONG.");
        System.out.println("Use yield() only to improve performance, not correctness.");
    }
}
