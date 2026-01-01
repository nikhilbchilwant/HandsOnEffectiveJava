package effectivejava.labs.chapter10.lab69;

/**
 * ============================================================================
 * LAB 69: Use Exceptions Only for Exceptional Conditions (Item 69)
 * ============================================================================
 * Chapter 10, pp. 293-296
 * 
 * SCENARIO:
 * Code uses exceptions for ordinary control flow, like detecting the end
 * of an array. This is WRONG — exceptions are for exceptional conditions!
 * 
 * YOUR TASK:
 * TODO: Rewrite exception-based loops to use normal control flow
 * ============================================================================
 */
public class ExceptionMisuse {

    // =========================================================================
    // HORRENDOUS: Using exception for loop termination!
    // =========================================================================
    
    public static void traverseArrayBad(String[] arr) {
        try {
            int i = 0;
            while (true) {
                System.out.println(arr[i++]);  // Throws at end
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
            // Expected "exceptional" condition? NO! This is flow control abuse!
        }
    }

    // =========================================================================
    // TODO: Fix with normal loop
    // =========================================================================
    
    public static void traverseArrayGood(String[] arr) {
        // TODO: Replace with:
        for (String s : arr) {
            System.out.println(s);
        }
    }

    // =========================================================================
    // BAD: Exception to check if element exists
    // =========================================================================
    
    public static boolean containsBad(String[] arr, String target) {
        try {
            for (int i = 0; ; i++) {
                if (arr[i].equals(target)) {
                    return true;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;  // Fell off end
        }
    }

    // =========================================================================
    // TODO: Fix with normal iteration
    // =========================================================================
    
    // public static boolean containsGood(String[] arr, String target) {
    //     for (String s : arr) {
    //         if (s.equals(target)) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    public static void main(String[] args) {
        String[] items = {"apple", "banana", "cherry"};

        System.out.println("=== Exception Misuse Demo ===\n");

        System.out.println("BAD: Exception-based traversal");
        long start = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            traverseArrayBad(items);
        }
        long badTime = System.nanoTime() - start;
        System.out.println("Time: " + badTime / 1_000_000.0 + " ms");

        System.out.println("\nGOOD: Normal loop traversal");
        start = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            traverseArrayGood(items);
        }
        long goodTime = System.nanoTime() - start;
        System.out.println("Time: " + goodTime / 1_000_000.0 + " ms");

        System.out.println("\n--- Why Bad? ---");
        System.out.println("1. Slower (exception handling is expensive)");
        System.out.println("2. Obscures intent");
        System.out.println("3. Can mask real bugs");
    }
}
