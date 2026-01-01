package effectivejava.labs.chapter12.lab88;

import java.io.*;
import java.util.Date;

/**
 * ============================================================================
 * LAB 88: Write readObject Methods Defensively (Item 88)
 * ============================================================================
 * Chapter 12, pp. 352-358
 * 
 * SCENARIO:
 * A class has invariants (e.g., start < end). Malicious byte streams can
 * violate invariants that constructors enforce. readObject must be defensive!
 * 
 * YOUR TASK:
 * TODO: Write defensive readObject that validates and copies
 * ============================================================================
 */
public class DefensiveReadObject {

    // =========================================================================
    // VULNERABLE: No validation in readObject
    // =========================================================================

    static class PeriodBad implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private final Date start;
        private final Date end;

        public PeriodBad(Date start, Date end) {
            if (start.compareTo(end) > 0) {
                throw new IllegalArgumentException("Start after end");
            }
            this.start = new Date(start.getTime());  // Defensive copy
            this.end = new Date(end.getTime());
        }

        // BUG: No readObject! 
        // Attacker can craft byte stream where end < start!
        // Or keep reference to the Date and mutate later!
    }

    // =========================================================================
    // SECURE: Defensive readObject
    // =========================================================================

    static class PeriodGood implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private Date start;  // Can't be final due to defensive copy
        private Date end;

        public PeriodGood(Date start, Date end) {
            this.start = new Date(start.getTime());
            this.end = new Date(end.getTime());
            if (this.start.compareTo(this.end) > 0) {
                throw new IllegalArgumentException("Start after end");
            }
        }

        // Defensive readObject
        private void readObject(ObjectInputStream s)
                throws IOException, ClassNotFoundException {
            s.defaultReadObject();  // Read stream normally

            // STEP 1: Make defensive copies BEFORE validation
            // (Prevents TOCTOU attack)
            start = new Date(start.getTime());
            end = new Date(end.getTime());

            // STEP 2: Validate invariants
            if (start.compareTo(end) > 0) {
                throw new InvalidObjectException("Start after end");
            }
        }

        // Also need defensive getters!
        public Date getStart() { return new Date(start.getTime()); }
        public Date getEnd() { return new Date(end.getTime()); }
    }

    // =========================================================================
    // readObject is like a constructor - treat it as such!
    // =========================================================================

    public static void main(String[] args) {
        System.out.println("=== Defensive readObject ===\n");

        System.out.println("readObject is a CONSTRUCTOR that takes a byte stream.");
        System.out.println("An attacker can handcraft ANY byte stream!");

        System.out.println("\nDefensive readObject pattern:");
        System.out.println("1. Call defaultReadObject()");
        System.out.println("2. Make defensive copies of mutable fields");
        System.out.println("3. Validate all invariants");
        System.out.println("4. Throw InvalidObjectException on failure");

        System.out.println("\nAlso remember:");
        System.out.println("- Can't use 'final' with defensive deserialization");
        System.out.println("- Copy BEFORE validation (TOCTOU)");
        System.out.println("- Don't use clone() - attacker can subclass");
    }
}
