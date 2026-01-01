package effectivejava.labs.chapter06.lab35;

/**
 * ============================================================================
 * LAB 35: Use Instance Fields Instead of Ordinals (Item 35)
 * ============================================================================
 * Chapter 6, pp. 168-169
 * 
 * SCENARIO:
 * An enum uses ordinal() to derive a value. This is FRAGILE - if you
 * reorder the enum constants, the derived values break!
 * 
 * YOUR TASK:
 * TODO: Replace ordinal() with an instance field
 * ============================================================================
 */
public class OrdinalProblem {

    // =========================================================================
    // BAD: Using ordinal() - breaks if reordered
    // =========================================================================

    enum EnsembleBad {
        SOLO, DUET, TRIO, QUARTET, QUINTET,
        SEXTET, SEPTET, OCTET, NONET, DECTET;

        // DON'T DO THIS!
        public int numberOfMusicians() {
            return ordinal() + 1;  // Fragile!
        }
        // If someone adds DOUBLE_QUARTET between OCTET and NONET,
        // all subsequent values are WRONG!
    }

    // =========================================================================
    // GOOD: Use instance field - independent of declaration order
    // =========================================================================

    enum Ensemble {
        SOLO(1), DUET(2), TRIO(3), QUARTET(4), QUINTET(5),
        SEXTET(6), SEPTET(7), OCTET(8), DOUBLE_QUARTET(8),  // Can add!
        NONET(9), DECTET(10);

        private final int numberOfMusicians;

        Ensemble(int size) {
            this.numberOfMusicians = size;
        }

        public int numberOfMusicians() {
            return numberOfMusicians;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== ordinal() Problems ===\n");

        System.out.println("BAD (using ordinal):");
        for (EnsembleBad e : EnsembleBad.values()) {
            System.out.printf("  %s: %d musicians%n", e, e.numberOfMusicians());
        }

        System.out.println("\nGOOD (using instance field):");
        for (Ensemble e : Ensemble.values()) {
            System.out.printf("  %s: %d musicians%n", e, e.numberOfMusicians());
        }

        System.out.println("\n--- Key Point ---");
        System.out.println("Never derive a value from ordinal()");
        System.out.println("Store it in an instance field instead");
        System.out.println("ordinal() is for EnumSet/EnumMap internals only");
    }
}
