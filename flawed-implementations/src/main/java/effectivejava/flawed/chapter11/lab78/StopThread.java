package effectivejava.flawed.chapter11.lab78;

/**
 * FLAWED IMPLEMENTATION - Classic visibility bug
 * 
 * This demonstrates the infamous "stop thread" problem where
 * a thread may never see changes to a shared variable because
 * of compiler/CPU optimizations.
 */
public class StopThread {

    // NOT volatile - changes may not be visible across threads!
    private boolean stopRequested = false;

    /**
     * Start a background thread that does work until stopped.
     * 
     * BUG: Without synchronization/volatile, the thread may NEVER
     * see that stopRequested was set to true, running forever!
     * 
     * The JVM is allowed to hoist the read out of the loop:
     *   if (!stopRequested)
     *       while (true) doWork();
     * 
     * This is called "hoisting" and is a valid optimization when
     * the JVM can prove (incorrectly, in multithreaded context)
     * that stopRequested never changes.
     */
    public void runBackgroundThread() throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            while (!stopRequested) {
                // Simulating work
                i++;
            }
            System.out.println("Background thread stopped after " + i + " iterations");
        });

        backgroundThread.start();

        // Give thread time to start and potentially get "stuck"
        Thread.sleep(100);

        // Request stop - but will the thread see this?
        requestStop();
        System.out.println("Stop requested, waiting for thread...");

        // Wait for thread to finish (with timeout to avoid hanging test)
        backgroundThread.join(2000);

        if (backgroundThread.isAlive()) {
            System.out.println("Thread is STILL RUNNING after stop request!");
            backgroundThread.interrupt(); // Force stop
        }
    }

    public void requestStop() {
        stopRequested = true;
    }

    public boolean isStopRequested() {
        return stopRequested;
    }

    // FIX OPTIONS:
    // 1. Make stopRequested volatile
    // 2. Synchronize both requestStop() and isStopRequested()
    //    (Remember: BOTH read and write must be synchronized)
    
    public static void main(String[] args) throws InterruptedException {
        new StopThread().runBackgroundThread();
    }
}
