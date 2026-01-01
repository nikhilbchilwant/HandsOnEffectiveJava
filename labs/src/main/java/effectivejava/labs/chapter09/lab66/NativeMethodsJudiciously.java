package effectivejava.labs.chapter09.lab66;

/**
 * ============================================================================
 * LAB 66: Use Native Methods Judiciously (Item 66)
 * ============================================================================
 * Chapter 9, pp. 285-286
 * 
 * SCENARIO:
 * Developers consider JNI for performance. Usually a bad idea!
 * 
 * YOUR TASK:
 * TODO: Understand when native methods are (rarely) appropriate
 * ============================================================================
 */
public class NativeMethodsJudiciously {

    // =========================================================================
    // What are native methods?
    // =========================================================================

    // Native methods are written in C/C++ and called via JNI
    // Example declaration:
    // public native long currentTimeMillis();

    // =========================================================================
    // When native methods MIGHT be needed
    // =========================================================================

    // 1. Access platform-specific facilities
    //    - Hardware features not exposed by Java
    //    - OS-specific APIs (Windows Registry, etc.)
    //    - BUT: Java now covers most cases!

    // 2. Access legacy native code libraries
    //    - Huge existing C/C++ codebase
    //    - Can't rewrite in Java

    // 3. Performance-critical sections (RARELY needed now!)
    //    - JVM is highly optimized
    //    - JIT compiler often matches native speed
    //    - Modern Java intrinsics handle most cases

    // =========================================================================
    // Why to AVOID native methods
    // =========================================================================

    public static void main(String[] args) {
        System.out.println("=== Native Methods ===\n");

        System.out.println("AVOID native methods because:");
        System.out.println("1. NOT safe - can corrupt memory");
        System.out.println("2. NOT portable - platform-specific");
        System.out.println("3. Hard to debug");
        System.out.println("4. GC can't track native memory");
        System.out.println("5. JNI glue code is error-prone");
        System.out.println("6. Performance: JNI calls have overhead!");

        System.out.println("\nRarely needed because:");
        System.out.println("- JVM is highly optimized now");
        System.out.println("- java.nio for performant I/O");
        System.out.println("- java.math for arbitrary precision");
        System.out.println("- Foreign Function API (Panama) in newer Java");

        System.out.println("\nIF you must use native:");
        System.out.println("- Minimize native code");
        System.out.println("- Test thoroughly on all platforms");
        System.out.println("- Have Java fallback if possible");

        System.out.println("\n--- Modern Alternatives ---");
        System.out.println("Java 22+: Foreign Function & Memory API");
        System.out.println("  (Safe, modern replacement for JNI)");
    }
}
