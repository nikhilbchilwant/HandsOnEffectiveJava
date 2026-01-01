package effectivejava.labs.chapter06.lab39;

import java.lang.annotation.*;
import java.lang.reflect.*;

/**
 * ============================================================================
 * LAB 39: Prefer Annotations to Naming Patterns (Item 39) 
 * ============================================================================
 * Chapter 6, pp. 180-188
 * 
 * SCENARIO:
 * Old JUnit 3 used naming patterns (methods starting with "test").
 * Modern frameworks use annotations - type-safe, documented, flexible!
 * 
 * YOUR TASK:
 * TODO: Understand annotation-based design vs naming patterns
 * ============================================================================
 */
public class AnnotationsVsNaming {

    // =========================================================================
    // BAD: Naming pattern approach
    // =========================================================================

    // Old JUnit 3 style - methods must be named "testXxx"
    // Problems:
    // 1. No compiler checking - typo like "tsetSomething" silently ignored
    // 2. No way to pass parameters
    // 3. Can't be applied to non-methods

    // =========================================================================
    // GOOD: Annotation approach
    // =========================================================================

    // Marker annotation for test methods
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Test {
    }

    // Annotation with parameter
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface ExceptionTest {
        Class<? extends Throwable> value();
    }

    // Sample tests
    static class Sample {
        @Test
        public static void m1() { }  // Test should pass

        public static void m2() { }  // Not a test - no annotation

        @Test
        public static void m3() {    // Test should fail
            throw new RuntimeException("Boom");
        }

        @ExceptionTest(ArithmeticException.class)
        public static void m4() {    // Test should pass
            int i = 0;
            int j = 1 / i;  // Division by zero
        }
    }

    // =========================================================================
    // Simple test runner
    // =========================================================================

    public static void runTests(Class<?> testClass) throws Exception {
        int tests = 0;
        int passed = 0;

        for (Method m : testClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Test.class)) {
                tests++;
                try {
                    m.invoke(null);
                    passed++;
                } catch (InvocationTargetException e) {
                    System.out.println(m + " failed: " + e.getCause());
                }
            }
            
            if (m.isAnnotationPresent(ExceptionTest.class)) {
                tests++;
                Class<? extends Throwable> expected = 
                    m.getAnnotation(ExceptionTest.class).value();
                try {
                    m.invoke(null);
                    System.out.println(m + " failed: no exception");
                } catch (InvocationTargetException e) {
                    if (expected.isInstance(e.getCause())) {
                        passed++;
                    } else {
                        System.out.println(m + " failed: wrong exception");
                    }
                }
            }
        }

        System.out.printf("Passed: %d/%d%n", passed, tests);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("=== Annotations vs Naming Patterns ===\n");

        runTests(Sample.class);

        System.out.println("\n--- Why Annotations are Better ---");
        System.out.println("1. Compiler-checked: typos are errors");
        System.out.println("2. Can have parameters");
        System.out.println("3. Self-documenting");
        System.out.println("4. Can be applied to various elements");
    }
}
