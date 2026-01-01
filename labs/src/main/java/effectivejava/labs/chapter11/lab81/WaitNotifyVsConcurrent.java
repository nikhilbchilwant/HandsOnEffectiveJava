package effectivejava.labs.chapter11.lab81;

import java.util.concurrent.*;

/**
 * ============================================================================
 * LAB 81: Prefer Concurrency Utilities to wait and notify (Item 81)
 * ============================================================================
 * Chapter 11, pp. 325-330
 * 
 * SCENARIO:
 * Old code uses wait/notify for thread coordination. Modern code should use
 * java.util.concurrent utilities instead.
 * 
 * YOUR TASK:
 * TODO: Replace wait/notify with CountDownLatch, CyclicBarrier, etc.
 * ============================================================================
 */
public class WaitNotifyVsConcurrent {

    // =========================================================================
    // BAD: Using wait/notify (complex, error-prone)
    // =========================================================================
    
    private boolean ready = false;

    public void waitForReadyOldWay() throws InterruptedException {
        synchronized (this) {
            while (!ready) {  // Must loop to guard against spurious wakeups!
                wait();
            }
        }
        System.out.println("Old way: Ready!");
    }

    public void signalReadyOldWay() {
        synchronized (this) {
            ready = true;
            notifyAll();  // Must notify while holding lock
        }
    }

    // =========================================================================
    // GOOD: Using CountDownLatch (simple, robust)
    // =========================================================================
    
    private final CountDownLatch startLatch = new CountDownLatch(1);

    public void waitForReadyNewWay() throws InterruptedException {
        startLatch.await();  // Simple!
        System.out.println("New way: Ready!");
    }

    public void signalReadyNewWay() {
        startLatch.countDown();  // Simple!
    }

    // =========================================================================
    // Example: Coordinating multiple workers
    // =========================================================================
    
    public static long timeWorkers(int nWorkers, Runnable action) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(nWorkers);
        
        CountDownLatch ready = new CountDownLatch(nWorkers);  // All workers ready
        CountDownLatch start = new CountDownLatch(1);          // Starter gun
        CountDownLatch done = new CountDownLatch(nWorkers);    // All workers done

        for (int i = 0; i < nWorkers; i++) {
            executor.execute(() -> {
                ready.countDown();  // Signal this worker is ready
                try {
                    start.await();  // Wait for start signal
                    action.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();  // Signal this worker is done
                }
            });
        }

        ready.await();      // Wait for all workers to be ready
        long startTime = System.nanoTime();
        start.countDown();  // Fire the starting gun!
        done.await();       // Wait for all workers to finish
        long endTime = System.nanoTime();

        executor.shutdown();
        return endTime - startTime;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("=== wait/notify vs Concurrency Utilities ===\n");

        // Example: Timing concurrent workers
        System.out.println("Timing 5 workers doing simple task...");
        long nanos = timeWorkers(5, () -> {
            // Simulated work
            try { Thread.sleep(100); } catch (InterruptedException ignored) {}
        });
        System.out.printf("Time: %.2f ms%n", nanos / 1_000_000.0);

        System.out.println("\n--- Modern Concurrency Utilities ---");
        System.out.println("CountDownLatch - one-shot barrier");
        System.out.println("CyclicBarrier - reusable barrier");
        System.out.println("Semaphore - counting semaphore");
        System.out.println("BlockingQueue - thread-safe queue");
        System.out.println("ConcurrentHashMap - thread-safe map");
    }
}
