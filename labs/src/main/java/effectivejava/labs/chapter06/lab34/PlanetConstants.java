package effectivejava.labs.chapter06.lab34;

/**
 * ============================================================================
 * LAB 34: Use Enums Instead of int Constants (Item 34)
 * ============================================================================
 * Chapter 6, pp. 157-168
 * 
 * SCENARIO:
 * Constants for planets are implemented as int values. This has problems:
 * - No type safety (can pass any int)
 * - No namespace (all constants are global)
 * - No behavior (can't add methods)
 * - No iteration (can't loop over all values)
 * 
 * YOUR TASK:
 * TODO: Convert to a proper enum with fields and methods
 * ============================================================================
 */
public class PlanetConstants {

    // =========================================================================
    // FIXME: int constants are type-unsafe!
    // =========================================================================
    
    public static final int MERCURY = 0;
    public static final int VENUS = 1;
    public static final int EARTH = 2;
    public static final int MARS = 3;
    public static final int JUPITER = 4;
    public static final int SATURN = 5;
    public static final int URANUS = 6;
    public static final int NEPTUNE = 7;

    // Parallel arrays — error-prone!
    private static final double[] MASS = {
        3.303e+23, 4.869e+24, 5.976e+24, 6.421e+23,
        1.900e+27, 5.688e+26, 8.686e+25, 1.024e+26
    };

    private static final double[] RADIUS = {
        2.4397e6, 6.0518e6, 6.37814e6, 3.3972e6,
        7.1492e7, 6.0268e7, 2.5559e7, 2.4746e7
    };

    public static double surfaceGravity(int planet) {
        double G = 6.67300E-11;
        return G * MASS[planet] / (RADIUS[planet] * RADIUS[planet]);
    }

    public static double surfaceWeight(int planet, double mass) {
        return mass * surfaceGravity(planet);
    }

    // =========================================================================
    // TODO: Create proper Planet enum
    // =========================================================================
    
    // public enum Planet {
    //     MERCURY(3.303e+23, 2.4397e6),
    //     VENUS(4.869e+24, 6.0518e6),
    //     EARTH(5.976e+24, 6.37814e6),
    //     MARS(6.421e+23, 3.3972e6),
    //     JUPITER(1.900e+27, 7.1492e7),
    //     SATURN(5.688e+26, 6.0268e7),
    //     URANUS(8.686e+25, 2.5559e7),
    //     NEPTUNE(1.024e+26, 2.4746e7);
    //     
    //     private final double mass;   // In kilograms
    //     private final double radius; // In meters
    //     private static final double G = 6.67300E-11;
    //     
    //     Planet(double mass, double radius) {
    //         this.mass = mass;
    //         this.radius = radius;
    //     }
    //     
    //     public double mass() { return mass; }
    //     public double radius() { return radius; }
    //     
    //     public double surfaceGravity() {
    //         return G * mass / (radius * radius);
    //     }
    //     
    //     public double surfaceWeight(double otherMass) {
    //         return otherMass * surfaceGravity();
    //     }
    // }

    public static void main(String[] args) {
        System.out.println("=== int Constants Problems ===\n");

        // PROBLEM 1: No type safety
        System.out.println("Surface gravity of EARTH:");
        System.out.println(surfaceGravity(EARTH));

        // Can pass wrong value!
        System.out.println("\nSurface gravity of '42' (invalid!):");
        try {
            System.out.println(surfaceGravity(42));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: " + e);
        }

        // PROBLEM 2: Can't iterate
        System.out.println("\nCan't iterate over planets with int constants.");
        System.out.println("// for (int planet : ???) - impossible!");

        System.out.println("\n--- With Enum ---");
        System.out.println("// for (Planet p : Planet.values()) {");
        System.out.println("//     System.out.println(p + \": \" + p.surfaceGravity());");
        System.out.println("// }");
    }
}
