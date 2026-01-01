package effectivejava.labs.chapter02.lab09;

import java.io.*;

/**
 * ============================================================================
 * LAB 09: Prefer try-with-resources to try-finally (Item 9)
 * ============================================================================
 * Chapter 2, pp. 34-36
 * 
 * SCENARIO:
 * Code uses nested try-finally for resource cleanup. This is:
 * - Verbose and error-prone
 * - Can mask exceptions (close exception hides original)
 * - Hard to get right with multiple resources
 * 
 * YOUR TASK:
 * TODO: Refactor to use try-with-resources
 * ============================================================================
 */
public class FileCopier {

    private static final int BUFFER_SIZE = 8192;

    // =========================================================================
    // FIXME: Nested try-finally is verbose and masks exceptions!
    // =========================================================================
    
    public static void copyFileBad(String src, String dst) throws IOException {
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                byte[] buf = new byte[BUFFER_SIZE];
                int n;
                while ((n = in.read(buf)) >= 0) {
                    out.write(buf, 0, n);
                }
            } finally {
                out.close();  // If this throws, original exception is LOST!
            }
        } finally {
            in.close();  // Same problem here
        }
    }

    // =========================================================================
    // SOLUTION: Bloch's try-with-resources example (Page 35)
    // =========================================================================
    
    // (Method removed: Implement copyFileGood below instead!)

    // =========================================================================
    // TODO: Refactor using try-with-resources
    // =========================================================================
    
    // public static void copyFileGood(String src, String dst) throws IOException {
    //     try (InputStream in = new FileInputStream(src);
    //          OutputStream out = new FileOutputStream(dst)) {
    //         byte[] buf = new byte[BUFFER_SIZE];
    //         int n;
    //         while ((n = in.read(buf)) >= 0) {
    //             out.write(buf, 0, n);
    //         }
    //     }
    //     // Both streams automatically closed!
    //     // If close() throws, it's a SUPPRESSED exception, not lost!
    // }

    // =========================================================================
    // BONUS: Reading first line (single resource)
    // =========================================================================
    
    // BEFORE: try-finally
    public static String firstLineOfFileBad(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        try {
            return br.readLine();
        } finally {
            br.close();
        }
    }

    // TODO: AFTER: try-with-resources
    // public static String firstLineOfFileGood(String path) throws IOException {
    //     try (BufferedReader br = new BufferedReader(new FileReader(path))) {
    //         return br.readLine();
    //     }
    // }

    public static void main(String[] args) {
        System.out.println("=== try-with-resources Demo ===\n");

        System.out.println("BEFORE (try-finally):");
        System.out.println("- Nested blocks for each resource");
        System.out.println("- Close exception can mask original");
        System.out.println("- Easy to forget to close");

        System.out.println("\nAFTER (try-with-resources):");
        System.out.println("- Single block handles all resources");
        System.out.println("- Suppressed exceptions preserved");
        System.out.println("- Automatic cleanup guaranteed");

        System.out.println("\nSuppressed exception access:");
        System.out.println("} catch (IOException e) {");
        System.out.println("    for (Throwable t : e.getSuppressed()) {");
        System.out.println("        // Handle suppressed exceptions");
        System.out.println("    }");
        System.out.println("}");
    }
}
