package effectivejava.labs.chapter09.lab65;

import java.lang.reflect.*;
import java.util.*;

/**
 * ============================================================================
 * LAB 65: Prefer Interfaces to Reflection (Item 65)
 * ============================================================================
 * Chapter 9, pp. 282-285
 * 
 * SCENARIO:
 * Code uses reflection for things that interfaces/classes would handle better.
 * Reflection has high costs: no compile-time checking, verbose, slow.
 * 
 * YOUR TASK:
 * TODO: Minimize reflection use; access objects through interfaces
 * ============================================================================
 */
public class ReflectionLimitations {

    // =========================================================================
    // BAD: Using reflection to invoke methods
    // =========================================================================

    public void callWithReflection(Object obj) {
        try {
            Method m = obj.getClass().getMethod("doSomething");
            m.invoke(obj);
        } catch (NoSuchMethodException e) {
            // Method might not exist - no compile-time check!
        } catch (IllegalAccessException | InvocationTargetException e) {
            // More runtime problems
        }
        // Problems:
        // - Verbose (6+ lines for one method call)
        // - No compile-time type checking
        // - Exceptions instead of compiler errors
        // - Slow (reflection is 10-100x slower)
    }

    // =========================================================================
    // GOOD: Use interface, instantiate reflectively if needed
    // =========================================================================

    interface Worker {
        void doWork();
    }

    // If you MUST use reflection, use it ONLY for instantiation
    // then access through interfaces
    public void useReflectionMinimally(String className) {
        Worker worker;
        
        try {
            Class<?> cl = Class.forName(className);
            worker = (Worker) cl.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found: " + className);
            return;
        } catch (Exception e) {
            System.err.println("Failed to instantiate: " + e);
            return;
        }

        // From here on, use the interface - no more reflection!
        worker.doWork();  // Compile-time checked!
    }

    // =========================================================================
    // Example: Reflective instantiation with interface access
    // =========================================================================

    // This pattern is used by ServiceLoader, dependency injection, etc.
    public static <T> T createInstance(Class<T> interfaceType, String implClass) {
        try {
            Class<?> impl = Class.forName(implClass);
            return interfaceType.cast(
                impl.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            throw new RuntimeException("Cannot create " + implClass, e);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Reflection Limitations ===\n");

        // Using the pattern: reflective instantiation, interface access
        Set<String> s = createInstance(Set.class, "java.util.HashSet");
        s.add("Hello");
        s.add("World");
        System.out.println("Created via reflection: " + s);

        System.out.println("\n--- Reflection Costs ---");
        System.out.println("1. No compile-time type checking");
        System.out.println("2. Verbose exception handling");
        System.out.println("3. Poor performance (10-100x slower)");
        System.out.println("4. Breaks encapsulation");

        System.out.println("\n--- When Reflection is OK ---");
        System.out.println("- Frameworks (DI, ORM)");
        System.out.println("- Tools (debuggers, analyzers)");
        System.out.println("- Instantiating class by name");
        System.out.println("\nEven then: use ONLY for instantiation!");
    }
}
