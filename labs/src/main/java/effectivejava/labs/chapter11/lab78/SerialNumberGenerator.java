package effectivejava.labs.chapter11.lab78;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ============================================================================
 * LAB 78 (Part 2): Atomicity Bug
 * ============================================================================
 * 
 * SCENARIO:
 * A serial number generator that must never return duplicate values.
 * The current implementation uses ++ which is NOT atomic!
 * 
 * THE BUG:
 * nextSerialNumber++ consists of THREE operations:
 *   1. Read current value
 *   2. Add 1
 *   3. Write new value
 * 
 * Race condition:
 *   Thread A reads 100
 *   Thread B reads 100
 *   Thread A writes 101
 *   Thread B writes 101  ← DUPLICATE!
 * 
 * YOUR TASK:
 * TODO: Fix using ONE of these approaches:
 * 
 * OPTION A - synchronized method:
 *   public synchronized int generateSerialNumber() { ... }
 * 
 * OPTION B - AtomicLong:
 *   private final AtomicLong nextSerialNumber = new AtomicLong();
 *   return (int) nextSerialNumber.getAndIncrement();
 * 
 * NOTE: volatile alone is NOT sufficient!
 * volatile provides visibility but NOT atomicity of compound operations.
 * 
 * VALIDATION:
 * Run main() - should report 0 duplicates after fix
 * ============================================================================
 */
public class SerialNumberGenerator {

    // =========================================================================
    // FIXME: ++ is not atomic! Use synchronized or AtomicLong
    // =========================================================================
    
    private int nextSerialNumber = 0;

    public int generateSerialNumber() {
        // FIXME: This is NOT atomic! Race condition causes duplicates!
        return nextSerialNumber++;
    }

    public int getCurrentValue() {
        return nextSerialNumber;
    }

    // =========================================================================
    // Stress test - demonstrates the bug
    // =========================================================================
    
    public static void main(String[] args) throws InterruptedException {
        SerialNumberGenerator generator = new SerialNumberGenerator();
        
        int threadCount = 10;
        int numbersPerThread = 10000;
        Set<Integer> generatedNumbers = ConcurrentHashMap.newKeySet();
        
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < numbersPerThread; j++) {
                    int num = generator.generateSerialNumber();
                    if (!generatedNumbers.add(num)) {
                        System.out.println("DUPLICATE: " + num);
                    }
                }
            });
        }
        
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
        
        int expected = threadCount * numbersPerThread;
        int actual = generatedNumbers.size();
        int lost = expected - actual;
        
        System.out.println("\n=== RESULTS ===");
        System.out.println("Expected unique numbers: " + expected);
        System.out.println("Actual unique numbers: " + actual);
        
        if (lost > 0) {
            System.out.println("LOST " + lost + " numbers to race conditions!");
            System.out.println("STATUS: BUG EXISTS - needs fixing!");
        } else {
            System.out.println("STATUS: All numbers unique - fix successful!");
        }
    }
}
