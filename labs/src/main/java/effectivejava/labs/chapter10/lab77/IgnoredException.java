package effectivejava.labs.chapter10.lab77;

import java.io.*;

/**
 * ============================================================================
 * LAB 77: Don't Ignore Exceptions (Item 77)
 * ============================================================================
 * Chapter 10, pp. 310
 * 
 * SCENARIO:
 * Code catches exceptions and does nothing — empty catch blocks.
 * This hides bugs, corrupts state, and makes debugging nightmares.
 * 
 * YOUR TASK:
 * TODO: Handle exceptions properly, or at minimum log them
 * ============================================================================
 */
public class IgnoredException {

    // =========================================================================
    // TERRIBLE: Empty catch block
    // =========================================================================
    
    public void readFileBad(String path) {
        try {
            FileReader reader = new FileReader(path);
            // read...
            reader.close();
        } catch (IOException e) {
            // DANGER: Swallowed exception!
            // File might not exist, but we pretend everything is fine
        }
    }

    // =========================================================================
    // STILL BAD: Just printing
    // =========================================================================
    
    public void readFileBetter(String path) {
        try {
            FileReader reader = new FileReader(path);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();  // Better, but still not great
            // - Output goes to stderr, may be lost
            // - No proper logging
            // - Code continues as if nothing happened
        }
    }

    // =========================================================================
    // GOOD: Proper handling
    // =========================================================================
    
    // Option 1: Propagate
    public void readFileGood1(String path) throws IOException {
        FileReader reader = new FileReader(path);
        reader.close();
        // Let caller handle it
    }

    // Option 2: Handle meaningfully
    public boolean readFileGood2(String path) {
        try {
            FileReader reader = new FileReader(path);
            reader.close();
            return true;
        } catch (IOException e) {
            // Log properly (use real logging in production)
            System.err.println("Failed to read " + path + ": " + e.getMessage());
            return false;  // Meaningful return value
        }
    }

    // Option 3: Wrap in unchecked if recovery impossible
    public void readFileGood3(String path) {
        try {
            FileReader reader = new FileReader(path);
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read " + path, e);
        }
    }

    // =========================================================================
    // When ignoring MIGHT be OK (rare!)
    // =========================================================================
    
    public void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException ignored) {
                // Document why this is OK!
                // Close failures on cleanup are often non-critical
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Don't Ignore Exceptions ===\n");

        IgnoredException demo = new IgnoredException();

        // This silently fails - DANGEROUS!
        System.out.println("Calling readFileBad with nonexistent file...");
        demo.readFileBad("nonexistent.txt");
        System.out.println("No error reported - bug hidden!");

        System.out.println("\n--- If you must ignore, document why ---");
        System.out.println("} catch (Exception ignored) {");
        System.out.println("    // Closing resource, failure is acceptable");
        System.out.println("}");
    }
}
