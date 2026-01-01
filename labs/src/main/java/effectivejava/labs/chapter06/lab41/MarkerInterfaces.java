package effectivejava.labs.chapter06.lab41;

import java.io.*;

/**
 * ============================================================================
 * LAB 41: Use Marker Interfaces to Define Types (Item 41)
 * ============================================================================
 * Chapter 6, pp. 190-192
 * 
 * SCENARIO:
 * Marker annotations vs marker interfaces - when to use which?
 * 
 * YOUR TASK:
 * TODO: Understand when marker interfaces are appropriate
 * ============================================================================
 */
public class MarkerInterfaces {

    // =========================================================================
    // Marker interface: defines a TYPE
    // =========================================================================

    // Serializable is a marker interface (no methods)
    // It marks a type that CAN be serialized

    static class MySerializable implements Serializable {
        private static final long serialVersionUID = 1L;
        private final String value;
        
        MySerializable(String value) { this.value = value; }
    }

    // Benefit: Compile-time type checking!
    static void writeToStreamTypeSafe(Serializable obj) {
        // We KNOW obj is serializable - method signature enforces it
    }

    // =========================================================================
    // Marker annotation: just metadata
    // =========================================================================

    // If you use annotation:
    @interface Persistable { }

    @Persistable
    static class MyAnnotated {
        private String value;
    }

    // No compile-time check! This accepts ANYTHING
    static void saveAnnotated(Object obj) {
        // Must check at runtime:
        if (!obj.getClass().isAnnotationPresent(Persistable.class)) {
            throw new IllegalArgumentException("Not persistable");
        }
    }

    // =========================================================================
    // When to use which
    // =========================================================================

    // Use MARKER INTERFACE when:
    // 1. You want to define a TYPE for type-checking
    // 2. The marker applies only to classes (not methods, fields, etc.)
    // 3. You might add methods later

    // Use MARKER ANNOTATION when:
    // 1. Marker can apply to non-class elements
    // 2. Part of a framework that uses annotations extensively
    // 3. No need for compile-time type checking

    public static void main(String[] args) {
        System.out.println("=== Marker Interfaces vs Annotations ===\n");

        // Type-safe with interface
        writeToStreamTypeSafe(new MySerializable("data"));
        // writeToStreamTypeSafe("raw string");  // Still compiled - String is Serializable!
        // writeToStreamTypeSafe(new Object());  // Compile error! Good!

        System.out.println("--- Marker Interface ---");
        System.out.println("✓ Defines a type (compile-time checking)");
        System.out.println("✓ Can add methods later");
        System.out.println("✗ Can only apply to classes");

        System.out.println("\n--- Marker Annotation ---");
        System.out.println("✓ Can apply to anything (methods, fields, etc.)");
        System.out.println("✓ Fits annotation-based frameworks");
        System.out.println("✗ No compile-time type checking");

        System.out.println("\nExamples:");
        System.out.println("  Interface: Serializable, Cloneable");
        System.out.println("  Annotation: @Override, @Deprecated");
    }
}
