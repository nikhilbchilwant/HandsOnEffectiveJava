package effectivejava.labs.chapter11.lab80;

import java.util.concurrent.*;

/**
 * ============================================================================
 * LAB 80: Prefer Executors, Tasks, and Streams to Threads (Item 80)
 * ============================================================================
 * Chapter 11, pp. 323-325
 * 
 * SCENARIO:
 * Old code creates raw threads. Modern Java has better abstractions:
 * Executor framework provides thread pools, task scheduling, etc.
 * 
 * YOUR TASK:
 * TODO: Replace raw Thread creation with ExecutorService
 * ============================================================================
 */
public class ExecutorVsThread {

    // =========================================================================
    // OLD WAY: Creating threads directly
    // =========================================================================
    
    public void runTasksOldWay() {
        // Creating threads is expensive!
        // No thread reuse, no bounded pool
        for (int i = 0; i < 100; i++) {
            final int taskNum = i;
            new Thread(() -> {
                System.out.println("Task " + taskNum + " running on " + 
                    Thread.currentThread().getName());
            }).start();
        }
        // Problems:
        // - 100 threads created (expensive)
        // - No bound on parallelism
        // - No exception handling
        // - Hard to shut down gracefully
    }

    // =========================================================================
    // NEW WAY: Using ExecutorService
    // =========================================================================
    
    public void runTasksNewWay() throws InterruptedException {
        // Fixed thread pool - reuses threads!
        ExecutorService executor = Executors.newFixedThreadPool(10);

        try {
            for (int i = 0; i < 100; i++) {
                final int taskNum = i;
                executor.submit(() -> {
                    System.out.println("Task " + taskNum + " running on " +
                        Thread.currentThread().getName());
                });
            }
        } finally {
            executor.shutdown();  // Graceful shutdown
            executor.awaitTermination(60, TimeUnit.SECONDS);
        }
        // Benefits:
        // - Only 10 threads created (pool)
        // - Threads reused for all 100 tasks
        // - Clean shutdown
        // - Can get Future for results
    }

    // =========================================================================
    // Even better: Using Callable for results
    // =========================================================================
    
    public void runTasksWithResults() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        try {
            Future<String> future = executor.submit(() -> {
                Thread.sleep(1000);  // Simulate work
                return "Result!";
            });

            System.out.println("Doing other work...");
            String result = future.get();  // Blocks until ready
            System.out.println("Got: " + result);
        } finally {
            executor.shutdown();
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("=== Executor vs Thread ===\n");

        ExecutorVsThread demo = new ExecutorVsThread();

        System.out.println("Using ExecutorService (better!):");
        demo.runTasksNewWay();

        System.out.println("\n--- Executor Types ---");
        System.out.println("newFixedThreadPool(n) - n threads");
        System.out.println("newCachedThreadPool() - grows as needed");
        System.out.println("newSingleThreadExecutor() - one thread");
        System.out.println("newScheduledThreadPool(n) - scheduled tasks");
    }
}
