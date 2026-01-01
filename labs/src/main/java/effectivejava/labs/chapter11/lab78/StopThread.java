package effectivejava.labs.chapter11.lab78;

/**
 * ============================================================================
 * LAB 78: Synchronize Access to Shared Mutable Data (Item 78)
 * ============================================================================
 * 
 * SCENARIO:
 * This is the famous "stop thread" problem. A thread checks a boolean flag
 * to know when to stop. Without proper synchronization, the thread may
 * NEVER see that the flag was changed!
 * 
 * THE BUG:
 * - Without volatile or synchronized, changes to stopRequested may not be 
 *   visible to other threads
 * - The JVM can "hoist" the read out of the loop:
 *   if (!stopRequested) while(true) { ... }  // Optimized to infinite loop!
 * 
 * YOUR TASK:
 * TODO: Fix this class using ONE of these approaches:
 * 
 * OPTION A - volatile keyword:
 *   private volatile boolean stopRequested = false;
 *   
 * OPTION B - synchronized accessor methods:
 *   private synchronized void requestStop() { stopRequested = true; }
 *   private synchronized boolean isStopRequested() { return stopRequested; }
 *   (BOTH read and write must be synchronized!)
 * 
 * KEY INSIGHT:
 * Synchronization is required for BOTH mutual exclusion AND visibility!
 * 
 * VALIDATION:
 * Run main() multiple times:
 * - Before fix: Thread may run forever (or get lucky and stop)
 * - After fix: Thread reliably stops within ~100ms
 * ============================================================================
 */
public class StopThread {

    // =========================================================================
    // FIXME: This field needs visibility guarantees!
    // TODO: Add 'volatile' keyword OR use synchronized accessors
    // =========================================================================
    
    private boolean stopRequested = false;

    public void runBackgroundThread() throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            // PROBLEM: JVM might hoist this read out of the loop!
            while (!stopRequested) {
                i++;
            }
            System.out.println("Background thread stopped after " + i + " iterations");
        });

        backgroundThread.start();

        // Give thread time to start
        Thread.sleep(100);

        // Request stop - but will the thread see this?
        requestStop();
        System.out.println("Stop requested, waiting for thread...");

        // Wait for thread to finish (with timeout)
        backgroundThread.join(2000);

        if (backgroundThread.isAlive()) {
            System.out.println("ERROR: Thread is STILL RUNNING after stop request!");
            System.out.println("The visibility bug occurred - thread never saw the flag change!");
            backgroundThread.interrupt();
        } else {
            System.out.println("SUCCESS: Thread stopped correctly!");
        }
    }

    // FIXME: This method's write may not be visible to other threads
    public void requestStop() {
        stopRequested = true;
    }

    // FIXME: This method's read may see stale value
    public boolean isStopRequested() {
        return stopRequested;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Testing visibility bug...");
        System.out.println("Run with: java -server StopThread");
        System.out.println("(Server JVM more likely to show the bug)\n");
        
        new StopThread().runBackgroundThread();
    }
}
