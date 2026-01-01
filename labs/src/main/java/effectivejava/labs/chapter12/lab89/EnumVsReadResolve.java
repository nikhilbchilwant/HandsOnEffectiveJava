package effectivejava.labs.chapter12.lab89;

import java.io.*;

/**
 * ============================================================================
 * LAB 89: For Instance Control, Prefer Enum Types to readResolve (Item 89)
 * ============================================================================
 * Chapter 12, pp. 358-362
 * 
 * SCENARIO:
 * A singleton implemented with readResolve() for serialization control.
 * Enum is simpler and more secure!
 * 
 * YOUR TASK:
 * TODO: Understand why enum is preferred for serializable singletons
 * ============================================================================
 */
public class EnumVsReadResolve {

    // =========================================================================
    // APPROACH 1: readResolve (complex, error-prone)
    // =========================================================================

    static class ElvisByReadResolve implements Serializable {
        private static final long serialVersionUID = 1L;
        
        public static final ElvisByReadResolve INSTANCE = new ElvisByReadResolve();
        
        private ElvisByReadResolve() { }

        // All fields must be transient or readResolve can be bypassed!
        // If any field is non-transient reference type, attacker can
        // substitute a different instance during deserialization.
        
        // This returns the canonical instance, discarding deserialized copy
        private Object readResolve() {
            return INSTANCE;
        }

        public void perform() {
            System.out.println("Elvis performs!");
        }
    }

    // =========================================================================
    // APPROACH 2: Enum (simple, secure, correct!)
    // =========================================================================

    enum Elvis {
        INSTANCE;

        public void perform() {
            System.out.println("Enum Elvis performs!");
        }
    }
    // That's it! JVM guarantees:
    // - Only one instance ever
    // - Serialization handled correctly
    // - Reflection-safe
    // - Thread-safe

    // =========================================================================
    // Demonstration
    // =========================================================================

    public static void testSerialization() throws Exception {
        // Serialize enum
        Elvis before = Elvis.INSTANCE;
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(before);
        oos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Elvis after = (Elvis) ois.readObject();

        System.out.println("Same instance? " + (before == after));  // true!
    }

    public static void main(String[] args) throws Exception {
        System.out.println("=== Enum vs readResolve ===\n");

        Elvis.INSTANCE.perform();

        testSerialization();

        System.out.println("\n--- Why Enum is Better ---");
        System.out.println("1. Simpler - just 'enum X { INSTANCE; }'");
        System.out.println("2. Secure - can't bypass with reflection");
        System.out.println("3. Correct - no subtle bugs with transient fields");
        System.out.println("4. Free - serialization handled by JVM");

        System.out.println("\nOnly use readResolve when:");
        System.out.println("- Instance-controlled class isn't an enum");
        System.out.println("- You can make ALL fields transient");
    }
}
