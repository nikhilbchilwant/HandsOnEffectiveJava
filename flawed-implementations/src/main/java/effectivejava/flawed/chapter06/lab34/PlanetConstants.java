package effectivejava.flawed.chapter06.lab34;

/**
 * FLAWED IMPLEMENTATION - int enum pattern for planets
 * 
 * This anti-pattern has many problems:
 * - No type safety (just ints)
 * - No namespace (pollutes wherever imported)
 * - No meaningful toString (prints number)
 * - No behavior (can't add methods to constants)
 * - No iteration (can't get all values)
 * - Brittle (client code embeds values)
 */
public class PlanetConstants {

    // Gravitational constant (m³ / kg s²)
    public static final double G = 6.67300E-11;

    // Planet constants - just integers!
    public static final int PLANET_MERCURY = 0;
    public static final int PLANET_VENUS = 1;
    public static final int PLANET_EARTH = 2;
    public static final int PLANET_MARS = 3;
    public static final int PLANET_JUPITER = 4;
    public static final int PLANET_SATURN = 5;
    public static final int PLANET_URANUS = 6;
    public static final int PLANET_NEPTUNE = 7;

    // Mass in kilograms
    private static final double[] MASS = {
        3.303e+23, 4.869e+24, 5.976e+24, 6.421e+23,
        1.900e+27, 5.688e+26, 8.686e+25, 1.024e+26
    };

    // Radius in meters
    private static final double[] RADIUS = {
        2.4397e6, 6.0518e6, 6.37814e6, 3.3972e6,
        7.1492e7, 6.0268e7, 2.5559e7, 2.4746e7
    };

    public static double getMass(int planet) {
        return MASS[planet];  // No bounds checking, no type checking!
    }

    public static double getRadius(int planet) {
        return RADIUS[planet];
    }

    public static double surfaceGravity(int planet) {
        return G * MASS[planet] / (RADIUS[planet] * RADIUS[planet]);
    }

    public static double surfaceWeight(int planet, double mass) {
        return mass * surfaceGravity(planet);
    }

    public static String getName(int planet) {
        switch (planet) {
            case PLANET_MERCURY: return "Mercury";
            case PLANET_VENUS: return "Venus";
            case PLANET_EARTH: return "Earth";
            case PLANET_MARS: return "Mars";
            case PLANET_JUPITER: return "Jupiter";
            case PLANET_SATURN: return "Saturn";
            case PLANET_URANUS: return "Uranus";
            case PLANET_NEPTUNE: return "Neptune";
            default: return "Unknown";  // Shouldn't happen, but...
        }
    }

    // Problems to observe:
    // 1. surfaceWeight(42, 80) - no compile error, runtime ArrayIndexOutOfBounds
    // 2. surfaceWeight(APPLE_FUJI, 80) - if someone defined that as 0, silently wrong
    // 3. System.out.println(PLANET_EARTH) prints "2", not "Earth"
    // 4. Can't iterate: for (int planet : ???) { }
    // 5. Adding PLANET_PLUTO requires updating ALL parallel arrays and switches
}
