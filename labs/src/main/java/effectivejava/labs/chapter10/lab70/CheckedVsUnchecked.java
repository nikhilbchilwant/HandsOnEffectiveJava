package effectivejava.labs.chapter10.lab70;

import java.io.*;
import java.net.*;

/**
 * ============================================================================
 * LAB 70: Use Checked Exceptions for Recoverable Conditions (Item 70)
 * ============================================================================
 * Chapter 10, pp. 296-298
 * 
 * SCENARIO:
 * Choosing between checked and unchecked exceptions incorrectly.
 * 
 * YOUR TASK:
 * TODO: Identify which exception type is appropriate for each case
 * ============================================================================
 */
public class CheckedVsUnchecked {

    // =========================================================================
    // CHECKED: Caller can reasonably recover
    // =========================================================================

    // FileNotFoundException is checked - caller can try a different file
    public void readConfig(String path) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException("Config not found: " + path);
            // Caller can:
            // - Use a default config
            // - Prompt user for a different path
            // - Create the file
        }
        // Read file...
    }

    // =========================================================================
    // UNCHECKED: Programming error, can't recover meaningfully
    // =========================================================================

    public void processArray(int[] array, int index) {
        // IndexOutOfBoundsException is unchecked - it's a bug!
        if (index < 0 || index >= array.length) {
            throw new ArrayIndexOutOfBoundsException(index);
            // Caller can't "recover" - the code is wrong
        }
        // Process array[index]...
    }

    // =========================================================================
    // COMMON MISTAKE: Checked exception for programming error
    // =========================================================================

    // DON'T DO THIS:
    // public void badExample(String s) throws InvalidInputException {
    //     if (s == null) {
    //         throw new InvalidInputException("s is null");
    //     }
    // }
    // If s is null, that's a bug! Use NullPointerException (unchecked)

    // =========================================================================
    // GOOD PATTERN: Provide state-testing method to avoid checked exception
    // =========================================================================

    interface Queue<E> {
        // Option 1: Checked exception (forces handling)
        E removeChecked() throws EmptyQueueException;
        
        // Option 2: State-testing method (avoids exception)
        boolean isEmpty();
        E remove();  // Throws unchecked if empty
    }

    static class EmptyQueueException extends Exception { }

    public static void main(String[] args) {
        System.out.println("=== Checked vs Unchecked Exceptions ===\n");

        System.out.println("CHECKED (extends Exception):");
        System.out.println("  - Caller CAN recover");
        System.out.println("  - Examples: IOException, SQLException");
        System.out.println("  - Forces caller to handle or declare");

        System.out.println("\nUNCHECKED (extends RuntimeException):");
        System.out.println("  - Programming error, can't recover");
        System.out.println("  - Examples: NullPointerException, IllegalArgumentException");
        System.out.println("  - Caller should fix the bug, not catch");

        System.out.println("\n--- Decision Tree ---");
        System.out.println("Can caller reasonably recover?");
        System.out.println("  YES → Checked exception");
        System.out.println("  NO (bug) → Unchecked exception / Error");
    }
}
