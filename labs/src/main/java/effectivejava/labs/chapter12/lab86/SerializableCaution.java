package effectivejava.labs.chapter12.lab86;

import java.io.*;

/**
 * ============================================================================
 * LAB 86: Implement Serializable with Great Caution (Item 86)
 * ============================================================================
 * Chapter 12, pp. 343-346
 * 
 * SCENARIO:
 * Class implements Serializable without understanding the costs.
 * 
 * YOUR TASK:
 * TODO: Understand when (not) to implement Serializable
 * ============================================================================
 */
public class SerializableCaution {

    // =========================================================================
    // COSTS of implementing Serializable
    // =========================================================================

    // Cost 1: Locks down the class's exported API forever
    static class PersonV1 implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String firstName;  // Can't change field names!
        private String lastName;   // Can't change types!
        // Adding/removing fields breaks compatibility
    }

    // Cost 2: Increases likelihood of bugs and security holes
    // - Deserialization is a "hidden constructor"
    // - Invariants not checked
    // - Attackers can craft malicious byte streams

    // Cost 3: Increases testing burden  
    // - Must test serialization/deserialization for each release
    // - Must test compatibility with old versions

    // =========================================================================
    // When to implement Serializable
    // =========================================================================

    // DO implement:
    // - Value classes (Date, BigInteger)
    // - Collection/framework classes that users need to serialize

    // DON'T implement:
    // - Classes designed for inheritance (unless carefully designed)
    // - Inner classes
    // - New production code (use JSON, Protocol Buffers, etc.)

    // =========================================================================
    // If you MUST implement Serializable
    // =========================================================================

    static class SaferSerializable implements Serializable {
        private static final long serialVersionUID = 123456789L;  // Explicit!
        
        private final String name;
        private final int age;
        
        public SaferSerializable(String name, int age) {
            // Validation in constructor
            if (age < 0) throw new IllegalArgumentException();
            this.name = name;
            this.age = age;
        }
        
        // readObject is like a constructor - validate!
        private void readObject(ObjectInputStream s) 
                throws IOException, ClassNotFoundException {
            s.defaultReadObject();
            // Re-validate invariants
            if (age < 0) {
                throw new InvalidObjectException("Negative age: " + age);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Serializable Caution ===\n");

        System.out.println("COSTS of Serializable:");
        System.out.println("1. Decreases flexibility: API locked forever");
        System.out.println("2. Increases bugs/security holes: hidden constructor");
        System.out.println("3. Increases testing: compatibility testing needed");

        System.out.println("\nAVOID unless:");
        System.out.println("- You're in a framework forcing it");
        System.out.println("- You need it for legacy reasons");

        System.out.println("\nINSTEAD use:");
        System.out.println("- JSON (Jackson, Gson)");
        System.out.println("- Protocol Buffers");
        System.out.println("- XML");
        System.out.println("- Database persistence");
    }
}
