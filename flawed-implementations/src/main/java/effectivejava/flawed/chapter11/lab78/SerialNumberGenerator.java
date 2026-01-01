package effectivejava.flawed.chapter11.lab78;

/**
 * FLAWED IMPLEMENTATION - Serial number generator with atomicity bug
 * 
 * This implementation uses unsynchronized increment, which is NOT atomic!
 * Under concurrent access, duplicate serial numbers can be generated.
 */
public class SerialNumberGenerator {

    // Without volatile or synchronization, visibility AND atomicity are broken
    private int nextSerialNumber = 0;

    /**
     * Generate the next serial number.
     * 
     * BUG: The increment operator (++) is NOT atomic!
     * It consists of three operations:
     *   1. Read current value
     *   2. Add 1
     *   3. Write new value
     * 
     * Race condition:
     *   Thread A reads 100
     *   Thread B reads 100
     *   Thread A writes 101
     *   Thread B writes 101  <-- DUPLICATE!
     *   
     * Even with volatile, this would be broken because volatile
     * only guarantees visibility, not atomicity of compound operations.
     */
    public int generateSerialNumber() {
        return nextSerialNumber++;
    }

    /**
     * Current value (for debugging).
     */
    public int getCurrentValue() {
        return nextSerialNumber;
    }

    // FIX OPTIONS:
    // 1. synchronized method or block
    // 2. java.util.concurrent.atomic.AtomicInteger
    // 
    // Note: Making the field volatile is NOT sufficient!
    // volatile int nextSerialNumber would still have the race condition
    // because ++ is still not atomic.
    
    public static void main(String[] args) throws InterruptedException {
        SerialNumberGenerator generator = new SerialNumberGenerator();
        
        // Stress test with multiple threads
        int threads = 10;
        int numbersPerThread = 1000;
        java.util.Set<Integer> generatedNumbers = java.util.concurrent.ConcurrentHashMap.newKeySet();
        
        Thread[] threadArray = new Thread[threads];
        for (int i = 0; i < threads; i++) {
            threadArray[i] = new Thread(() -> {
                for (int j = 0; j < numbersPerThread; j++) {
                    int num = generator.generateSerialNumber();
                    if (!generatedNumbers.add(num)) {
                        System.out.println("DUPLICATE DETECTED: " + num);
                    }
                }
            });
        }
        
        for (Thread t : threadArray) t.start();
        for (Thread t : threadArray) t.join();
        
        int expected = threads * numbersPerThread;
        int actual = generatedNumbers.size();
        System.out.printf("Expected %d unique numbers, got %d%n", expected, actual);
        if (actual < expected) {
            System.out.printf("LOST %d numbers to race conditions!%n", expected - actual);
        }
    }
}
