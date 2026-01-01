package effectivejava.labs.chapter02.lab04;

/**
 * ============================================================================
 * LAB 04: Enforce Noninstantiability with Private Constructor (Item 4)
 * ============================================================================
 * Chapter 2, pp. 19-20
 * 
 * SCENARIO:
 * You have utility classes with only static methods (like java.util.Collections).
 * These should NEVER be instantiated!
 * 
 * PROBLEM:
 * Without a private constructor, the compiler provides a default public one,
 * allowing meaningless instantiation.
 * 
 * YOUR TASK:
 * TODO #1: Add a private constructor that throws AssertionError
 * TODO #2: Add a comment explaining WHY the constructor exists
 * ============================================================================
 */
public class MathUtils {

    // FIXME: Missing private constructor!
    // Anyone can do: new MathUtils() — which is pointless!

    // TODO: Add this:
    // private MathUtils() {
    //     throw new AssertionError("No instances!");
    // }

    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static int lcm(int a, int b) {
        return (a * b) / gcd(a, b);
    }

    public static boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public static long factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be >= 0");
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static void main(String[] args) {
        // These static calls are correct:
        System.out.println("gcd(48, 18) = " + gcd(48, 18));
        System.out.println("isPrime(17) = " + isPrime(17));

        // This instantiation is POINTLESS and should be prevented:
        MathUtils useless = new MathUtils();  // FIXME: Should not compile!
        System.out.println("Created useless instance: " + useless);
        System.out.println("\nAfter fix, the line above should cause compile error!");
    }
}
