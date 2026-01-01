package effectivejava.flawed.chapter06.lab34;

/**
 * FLAWED IMPLEMENTATION - Demonstrates type confusion with int constants
 * 
 * When everything is an int, the compiler can't help you avoid errors.
 */
public class AppleOrangeConfusion {

    // Apple types
    public static final int APPLE_FUJI = 0;
    public static final int APPLE_PIPPIN = 1;
    public static final int APPLE_GRANNY_SMITH = 2;

    // Orange types
    public static final int ORANGE_NAVEL = 0;
    public static final int ORANGE_TEMPLE = 1;
    public static final int ORANGE_BLOOD = 2;

    // Color constants (reusing same values - collision!)
    public static final int COLOR_RED = 0;
    public static final int COLOR_GREEN = 1;
    public static final int COLOR_BLUE = 2;

    public static void main(String[] args) {
        // PROBLEM 1: Can compare apples to oranges (literally!)
        // This should be a compile error, but it's not:
        if (APPLE_FUJI == ORANGE_NAVEL) {
            System.out.println("Apple equals orange? This compiles!");  // Prints!
        }

        // PROBLEM 2: Can pass wrong type to method
        // Method expects apple, gets orange, no compile error:
        printAppleInfo(ORANGE_BLOOD);  // Compiles fine, makes no sense

        // PROBLEM 3: Namespace collision
        // All these are just ints in global namespace:
        int x = APPLE_FUJI + ORANGE_NAVEL + COLOR_RED;  // Nonsensical but compiles

        // PROBLEM 4: No meaningful printing
        System.out.println("Apple type: " + APPLE_FUJI);  // Prints "0"
        System.out.println("Orange type: " + ORANGE_NAVEL);  // Also prints "0"!

        // PROBLEM 5: Can use out-of-range values
        printAppleInfo(999);  // No compile error, undefined behavior
    }

    /**
     * Expects an apple type, but int parameter accepts anything.
     */
    public static void printAppleInfo(int appleType) {
        System.out.println("Apple type: " + appleType);
        // No way to validate it's actually an apple constant!
    }

    /**
     * Ordering sauce by "pepper" level - nonsensical but compiles.
     */
    public static void orderWithPepper(int pepperLevel) {
        // Someone calls: orderWithPepper(APPLE_FUJI);
        // Compiles, runs, is completely wrong
    }
}
