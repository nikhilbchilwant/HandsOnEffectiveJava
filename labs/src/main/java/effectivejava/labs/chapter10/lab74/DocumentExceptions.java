package effectivejava.labs.chapter10.lab74;

import java.io.*;

/**
 * ============================================================================
 * LAB 74: Document All Exceptions Thrown by Each Method (Item 74)
 * ============================================================================
 * Chapter 10, pp. 304-306
 * 
 * SCENARIO:
 * Methods throw exceptions but don't document them.
 * Users don't know what to catch or when.
 * 
 * YOUR TASK:
 * TODO: Document all exceptions with @throws
 * ============================================================================
 */
public class DocumentExceptions {

    // =========================================================================
    // BAD: No documentation
    // =========================================================================

    public void processFileBad(String path) throws IOException {
        // Throws IOException somehow... but when? What causes it?
        new FileReader(path).close();
    }

    // =========================================================================
    // GOOD: Documented checked exceptions
    // =========================================================================

    /**
     * Processes the file at the given path.
     * 
     * @param path the path to the file
     * @throws FileNotFoundException if the file does not exist
     * @throws IOException if an I/O error occurs reading the file
     * @throws NullPointerException if path is null
     */
    public void processFileGood(String path) throws IOException {
        if (path == null) {
            throw new NullPointerException("path must not be null");
        }
        try (FileReader reader = new FileReader(path)) {
            // process...
        }
    }

    // =========================================================================
    // Document unchecked exceptions too!
    // =========================================================================

    /**
     * Returns the element at the specified index.
     * 
     * @param index the index of the element to return
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if index is out of range
     *         ({@code index < 0 || index >= size()})
     */
    public Object get(int index) {
        // ...
        throw new IndexOutOfBoundsException("Index: " + index);
    }

    // =========================================================================
    // Don't just say "throws Exception"!
    // =========================================================================

    // BAD:
    // public void bad() throws Exception { }

    // BAD:
    // public void alsoBAd() throws Throwable { }

    // GOOD: List specific exceptions
    // public void good() throws IOException, SQLException { }

    public static void main(String[] args) {
        System.out.println("=== Document Exceptions ===\n");

        System.out.println("For each method, document:");
        System.out.println("1. Every CHECKED exception thrown");
        System.out.println("2. Important UNCHECKED exceptions");
        System.out.println();
        System.out.println("Use @throws for EACH exception:");
        System.out.println("  @throws IllegalArgumentException if x < 0");
        System.out.println("  @throws NullPointerException if name is null");
        System.out.println();
        System.out.println("DON'T:");
        System.out.println("- throws Exception (too vague)");
        System.out.println("- throws Throwable (even worse)");
    }
}
