package effectivejava.labs.chapter11.lab83;

/**
 * ============================================================================
 * LAB 83: Use Lazy Initialization Judiciously (Item 83)
 * ============================================================================
 * Chapter 11, pp. 333-336
 * 
 * SCENARIO:
 * You need to lazily initialize a field. The naive approach has race
 * conditions. Proper approaches include holder idiom and double-check.
 * 
 * YOUR TASK:
 * TODO #1: For static fields, use lazy initialization holder class
 * TODO #2: For instance fields, use double-checked locking correctly
 * TODO #3: For single-check OK cases, use volatile alone
 * ============================================================================
 */
public class LazyInit {

    // =========================================================================
    // BAD: Race condition in lazy init
    // =========================================================================
    
    private ExpensiveObject instance;  // Not volatile!

    // FIXME: Race condition!
    public ExpensiveObject getInstanceBad() {
        if (instance == null) {
            // Two threads can both see null and create two instances!
            instance = new ExpensiveObject();
        }
        return instance;
    }

    // =========================================================================
    // BAD: Synchronized is correct but slow
    // =========================================================================
    
    public synchronized ExpensiveObject getInstanceSlow() {
        if (instance == null) {
            instance = new ExpensiveObject();
        }
        return instance;  // Every call pays synchronization cost!
    }

    // =========================================================================
    // GOOD: Double-checked locking (instance fields)
    // =========================================================================
    
    // private volatile ExpensiveObject instanceDCL;  // MUST be volatile!
    //
    // public ExpensiveObject getInstanceDCL() {
    //     ExpensiveObject result = instanceDCL;
    //     if (result == null) {
    //         synchronized (this) {
    //             result = instanceDCL;
    //             if (result == null) {
    //                 instanceDCL = result = new ExpensiveObject();
    //             }
    //         }
    //     }
    //     return result;
    // }

    // =========================================================================
    // BEST: Holder class idiom (static fields) - JVM guarantees thread safety
    // =========================================================================
    
    // private static class Holder {
    //     static final ExpensiveObject INSTANCE = new ExpensiveObject();
    // }
    //
    // public static ExpensiveObject getInstanceStatic() {
    //     return Holder.INSTANCE;  // Lazy: Holder not loaded until first access
    // }

    static class ExpensiveObject {
        ExpensiveObject() {
            System.out.println("Creating expensive object...");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Lazy Initialization ===\n");

        LazyInit demo = new LazyInit();

        System.out.println("First access:");
        demo.getInstanceBad();

        System.out.println("Second access:");
        demo.getInstanceBad();

        System.out.println("\n--- Guidelines ---");
        System.out.println("1. Most fields don't need lazy init!");
        System.out.println("2. Static field: Use holder class idiom");
        System.out.println("3. Instance field: Use double-checked locking");
        System.out.println("4. Single-check OK: Use volatile only");
    }
}
