package effectivejava.flawed.chapter02.lab09;

import java.io.*;

/**
 * FLAWED IMPLEMENTATION - Try-finally with multiple resources
 * 
 * This file copier uses try-finally to manage resources, which leads to:
 * - Verbose, nested code
 * - Exception masking (close() exceptions hide original exceptions)
 * - Error-prone resource cleanup
 */
public class FileCopier {

    private static final int BUFFER_SIZE = 8192;

    /**
     * FLAWED: Nested try-finally is hard to read and error-prone.
     * 
     * Problems:
     * 1. If both read() throws AND close() throws, read() exception is lost!
     * 2. Deeply nested code is hard to follow
     * 3. Easy to forget to close one of the resources
     */
    public static void copy(String src, String dst) throws IOException {
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
                out.close();  // If this throws, original exception is masked!
            }
        } finally {
            in.close();  // Same problem here
        }
    }

    /**
     * ALSO FLAWED: Single try-finally doesn't properly handle multiple resources.
     * 
     * Problem: If in.close() throws, out.close() never called!
     */
    public static void copyBroken(String src, String dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
        try {
            byte[] buf = new byte[BUFFER_SIZE];
            int n;
            while ((n = in.read(buf)) >= 0) {
                out.write(buf, 0, n);
            }
        } finally {
            in.close();   // If this throws...
            out.close();  // This is never reached! Resource leak!
        }
    }

    /**
     * "FIXED" but still wrong: Swallowing exceptions is not the answer.
     */
    public static void copySwallowed(String src, String dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
        try {
            byte[] buf = new byte[BUFFER_SIZE];
            int n;
            while ((n = in.read(buf)) >= 0) {
                out.write(buf, 0, n);
            }
        } finally {
            try { in.close(); } catch (IOException ignored) { }   // Bad!
            try { out.close(); } catch (IOException ignored) { }  // Bad!
            // Now we ignore potentially important errors!
        }
    }

    // What we want:
    // 1. Both resources always closed
    // 2. Original exception preserved
    // 3. Close exceptions accessible as "suppressed"
    // 4. Clean, simple code
    //
    // Solution: try-with-resources does all of this automatically!
}
