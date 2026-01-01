package effectivejava.labs.chapter12.lab90;

import java.io.*;
import java.util.Date;

/**
 * ============================================================================
 * LAB 90: Consider Serialization Proxies (Item 90)
 * ============================================================================
 * Chapter 12, pp. 363-366
 * 
 * SCENARIO:
 * Serialization proxy pattern provides the most robust way to serialize
 * objects while maintaining invariants.
 * 
 * YOUR TASK:
 * TODO: Implement serialization proxy pattern
 * ============================================================================
 */
public class SerializationProxy {

    // =========================================================================
    // Serialization Proxy pattern
    // =========================================================================

    static final class Period implements Serializable {
        private final Date start;
        private final Date end;

        public Period(Date start, Date end) {
            this.start = new Date(start.getTime());
            this.end = new Date(end.getTime());
            if (this.start.compareTo(this.end) > 0) {
                throw new IllegalArgumentException("Start after end");
            }
        }

        public Date getStart() { return new Date(start.getTime()); }
        public Date getEnd() { return new Date(end.getTime()); }

        // =====================================================================
        // THE PROXY
        // =====================================================================

        // Nested class for serialization - minimal, just the logical state
        private static class SerializationProxyClass implements Serializable {
            private static final long serialVersionUID = 1L;
            
            private final Date start;
            private final Date end;

            SerializationProxyClass(Period p) {
                this.start = p.start;
                this.end = p.end;
            }

            // Recreates the enclosing instance using its PUBLIC constructor
            private Object readResolve() {
                return new Period(start, end);  // Uses normal constructor!
            }
        }

        // When serializing, write the proxy instead
        private Object writeReplace() {
            return new SerializationProxyClass(this);
        }

        // Prevent direct deserialization of Period
        private void readObject(ObjectInputStream stream)
                throws InvalidObjectException {
            throw new InvalidObjectException("Proxy required");
        }
    }

    // =========================================================================
    // Benefits
    // =========================================================================

    // 1. Uses normal constructor - invariants automatically enforced
    // 2. No extralinguistic object creation
    // 3. Works with final fields
    // 4. Resistant to attacks

    // =========================================================================
    // Limitation: Doesn't work well with inheritance
    // =========================================================================

    public static void main(String[] args) throws Exception {
        System.out.println("=== Serialization Proxy Pattern ===\n");

        // Create and serialize
        Period original = new Period(new Date(0), new Date(1000));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(original);
        oos.close();

        // Deserialize
        ObjectInputStream ois = new ObjectInputStream(
            new ByteArrayInputStream(baos.toByteArray()));
        Period copy = (Period) ois.readObject();

        System.out.println("Original: " + original.getStart() + " - " + original.getEnd());
        System.out.println("Copy: " + copy.getStart() + " - " + copy.getEnd());

        System.out.println("\n--- Pattern Structure ---");
        System.out.println("1. Private static SerializationProxy class");
        System.out.println("2. writeReplace() returns proxy");
        System.out.println("3. readObject() throws exception");
        System.out.println("4. Proxy's readResolve() uses public constructor");

        System.out.println("\n--- Benefits ---");
        System.out.println("- Uses normal constructor (invariants checked)");
        System.out.println("- Works with final fields");
        System.out.println("- Most robust serialization approach");
    }
}
