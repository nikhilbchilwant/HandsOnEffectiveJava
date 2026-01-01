package effectivejava.flawed.chapter02.lab03;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * DEMONSTRATION - How to break various singleton implementations
 * 
 * Run this class to see the vulnerabilities in the flawed implementations.
 * Your refactored versions should resist these attacks.
 */
public class SingletonBreaker {

    public static void main(String[] args) throws Exception {
        System.out.println("=== SINGLETON VULNERABILITY DEMONSTRATIONS ===\n");

        demonstrateReflectionAttack();
        demonstrateSerializationAttack();
        demonstrateLazyInitRaceCondition();
    }

    /**
     * ATTACK 1: Use reflection to create a second instance
     */
    private static void demonstrateReflectionAttack() throws Exception {
        System.out.println("--- Reflection Attack ---");

        ConfigurationManager original = ConfigurationManager.INSTANCE;
        System.out.println("Original instance: " + original);

        // Get the private constructor
        Constructor<ConfigurationManager> constructor =
                ConfigurationManager.class.getDeclaredConstructor();

        // Make it accessible (bypass private)
        constructor.setAccessible(true);

        // Create a second instance!
        ConfigurationManager hacked = constructor.newInstance();
        System.out.println("Hacked instance:   " + hacked);

        // Verify they're different
        System.out.println("Same instance? " + (original == hacked));
        System.out.println("Singleton BROKEN via reflection!\n");
    }

    /**
     * ATTACK 2: Serialize and deserialize to create a new instance
     */
    private static void demonstrateSerializationAttack() throws Exception {
        System.out.println("--- Serialization Attack ---");

        ConfigurationManager original = ConfigurationManager.INSTANCE;
        original.setProperty("secret", "password123");
        System.out.println("Original instance: " + original);
        System.out.println("Original has 'secret': " + original.getProperty("secret"));

        // Serialize
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(original);
        oos.close();

        // Deserialize - creates a NEW instance!
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        ConfigurationManager deserialized = (ConfigurationManager) ois.readObject();
        ois.close();

        System.out.println("Deserialized:      " + deserialized);
        System.out.println("Same instance? " + (original == deserialized));
        System.out.println("Deserialized has 'secret': " + deserialized.getProperty("secret"));
        System.out.println("Singleton BROKEN via serialization!\n");

        // Note: The deserialized instance was reconstructed, losing the property
        // we added to the original after initial construction!
    }

    /**
     * ATTACK 3: Race condition in lazy initialization
     */
    private static void demonstrateLazyInitRaceCondition() throws Exception {
        System.out.println("--- Lazy Init Race Condition ---");

        // Reset the lazy singleton (using reflection for demo purposes)
        java.lang.reflect.Field instanceField = 
                LazyConfigManager.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);

        int threadCount = 100;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(threadCount);
        Set<LazyConfigManager> instances = ConcurrentHashMap.newKeySet();

        // Create threads that all try to get the instance at the same time
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    startLatch.await(); // Wait for signal
                    LazyConfigManager instance = LazyConfigManager.getInstance();
                    instances.add(instance);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    doneLatch.countDown();
                }
            });
        }

        // Fire!
        startLatch.countDown();
        doneLatch.await();
        executor.shutdown();

        System.out.println("Threads: " + threadCount);
        System.out.println("Unique instances created: " + instances.size());
        
        if (instances.size() > 1) {
            System.out.println("Singleton BROKEN via race condition!");
            System.out.println("Instances:");
            instances.forEach(i -> System.out.println("  " + i));
        } else {
            System.out.println("Got lucky this time - but race condition still exists!");
        }
        System.out.println();
    }
}
