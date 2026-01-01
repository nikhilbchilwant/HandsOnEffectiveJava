package effectivejava.labs.chapter02.lab08;

/**
 * ============================================================================
 * LAB 08: Avoid Finalizers and Cleaners (Item 8)
 * ============================================================================
 * Chapter 2, pp. 29-34
 * 
 * SCENARIO:
 * Code uses finalize() for resource cleanup. This is DANGEROUS:
 * - No guarantee when (or if!) finalizer runs
 * - Severe performance penalty
 * - Security vulnerabilities
 * 
 * YOUR TASK:
 * TODO #1: Remove finalize() method
 * TODO #2: Implement AutoCloseable instead
 * TODO #3: Use try-with-resources for cleanup
 * ============================================================================
 */
public class ResourceHolder {

    private final String resourceName;
    private boolean closed = false;

    public ResourceHolder(String name) {
        this.resourceName = name;
        System.out.println("Acquired resource: " + name);
    }

    public void doWork() {
        if (closed) {
            throw new IllegalStateException("Resource is closed!");
        }
        System.out.println("Working with: " + resourceName);
    }

    // =========================================================================
    // FIXME: Using finalize() is BAD!
    // =========================================================================
    
    @Override
    @SuppressWarnings("removal")  // Finalize is deprecated for removal
    protected void finalize() throws Throwable {
        try {
            // PROBLEM 1: This may NEVER run!
            // PROBLEM 2: If it runs, timing is unpredictable
            // PROBLEM 3: Severe performance penalty
            // PROBLEM 4: Security vulnerability (finalizer attack)
            if (!closed) {
                System.out.println("FINALIZER: Releasing " + resourceName);
                close();
            }
        } finally {
            super.finalize();
        }
    }

    public void close() {
        if (!closed) {
            System.out.println("Closing resource: " + resourceName);
            closed = true;
        }
    }

    // =========================================================================
    // TODO: Refactor to implement AutoCloseable
    // =========================================================================
    
    // public class ResourceHolder implements AutoCloseable {
    //     
    //     @Override
    //     public void close() {
    //         if (!closed) {
    //             System.out.println("Closing: " + resourceName);
    //             closed = true;
    //         }
    //     }
    // }
    //
    // Usage with try-with-resources:
    // try (ResourceHolder r = new ResourceHolder("DB Connection")) {
    //     r.doWork();
    // }  // Automatically closed here!

    public static void main(String[] args) {
        System.out.println("=== Finalizer Problems Demo ===\n");

        // Create resource without proper cleanup
        ResourceHolder holder = new ResourceHolder("Important File");
        holder.doWork();

        // "Forget" to close it - hope finalizer saves us
        holder = null;  // Eligible for GC

        System.out.println("\nRequesting GC (finalizer may or may not run)...");
        System.gc();

        // Wait a bit for finalizer
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {}

        System.out.println("\n--- Proper Approach ---");
        System.out.println("Use try-with-resources with AutoCloseable!");
        System.out.println("// try (Resource r = new Resource()) { r.use(); }");
    }
}
