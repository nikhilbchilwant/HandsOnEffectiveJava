package effectivejava.labs.chapter04.lab22;

/**
 * ============================================================================
 * LAB 22: Use Interfaces Only to Define Types (Item 22)
 * ============================================================================
 * Chapter 4, pp. 107-109
 * 
 * SCENARIO:
 * A "constant interface" is used to define constants. This is an antipattern!
 * 
 * YOUR TASK:
 * TODO: Replace constant interface with proper alternatives
 * ============================================================================
 */
public class ConstantInterface {

    // =========================================================================
    // BAD: Constant interface antipattern
    // =========================================================================

    // DON'T DO THIS!
    interface PhysicalConstantsBad {
        double AVOGADROS_NUMBER = 6.022_140_76e23;
        double BOLTZMANN_CONSTANT = 1.380_648_52e-23;
        double ELECTRON_MASS = 9.109_383_56e-31;
    }

    // Problems:
    // 1. Implementation detail leaks into API
    // 2. Confuses users (looks like a type)
    // 3. Pollutes namespace when implemented
    // 4. Binary compatibility issues

    // =========================================================================
    // GOOD: Use utility class with private constructor
    // =========================================================================

    final class PhysicalConstants {
        private PhysicalConstants() {}  // Prevent instantiation

        public static final double AVOGADROS_NUMBER = 6.022_140_76e23;
        public static final double BOLTZMANN_CONSTANT = 1.380_648_52e-23;
        public static final double ELECTRON_MASS = 9.109_383_56e-31;
    }

    // =========================================================================
    // GOOD: Enum if constants form a set of related values
    // =========================================================================

    enum Planet {
        MERCURY(3.303e+23, 2.4397e6),
        VENUS(4.869e+24, 6.0518e6),
        EARTH(5.976e+24, 6.37814e6);

        private final double mass;
        private final double radius;

        Planet(double mass, double radius) {
            this.mass = mass;
            this.radius = radius;
        }

        public double mass() { return mass; }
        public double radius() { return radius; }
    }

    // =========================================================================
    // Using constants with static import
    // =========================================================================

    public void calculateExample() {
        // Without static import
        double atoms = 1.0 / PhysicalConstants.AVOGADROS_NUMBER;

        // With static import (add at top of file):
        // import static pkg.PhysicalConstants.*;
        // double atoms = 1.0 / AVOGADROS_NUMBER;
    }

    public static void main(String[] args) {
        System.out.println("=== Constant Interface Antipattern ===\n");

        System.out.println("DON'T: interface Constants { int X = 1; }");
        System.out.println();
        System.out.println("DO:");
        System.out.println("  1. Utility class with private constructor");
        System.out.println("  2. Enum if constants are related");
        System.out.println("  3. Add to relevant class if tightly associated");

        System.out.println("\nUse static import for clean code:");
        System.out.println("  import static pkg.PhysicalConstants.*;");
    }
}
