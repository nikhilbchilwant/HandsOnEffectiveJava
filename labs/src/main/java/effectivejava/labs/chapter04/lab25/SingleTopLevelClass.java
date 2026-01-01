package effectivejava.labs.chapter04.lab25;

/**
 * ============================================================================
 * LAB 25: Limit Source Files to a Single Top-Level Class (Item 25)
 * ============================================================================
 * Chapter 4, pp. 115-116
 * 
 * SCENARIO:
 * Multiple top-level classes in one file - confusing and can cause errors.
 * 
 * YOUR TASK:
 * TODO: Understand why one class per file is the right approach
 * ============================================================================
 */
public class SingleTopLevelClass {

    // =========================================================================
    // This is FINE - nested classes in one file
    // =========================================================================

    // Static member class
    static class Helper {
        void help() { System.out.println("Helping!"); }
    }

    // =========================================================================
    // This would be BAD in a real file:
    // =========================================================================

    // DON'T PUT THIS IN THE SAME FILE:
    // 
    // // Second top-level class - BAD!
    // class Utensil {
    //     static final String NAME = "pan";
    // }
    // 
    // class Dessert {
    //     static final String NAME = "cake";
    // }
    //
    // Problems:
    // 1. Which class is the "main" one? 
    // 2. Order of compilation matters!
    // 3. If both Utensil.java and Main.java define Utensil,
    //    behavior depends on which is compiled first!

    // =========================================================================
    // If you need small helper classes, use:
    // =========================================================================

    // Option 1: Static member classes (like Helper above)

    // Option 2: Private classes for implementation details
    private static class InternalHelper {
        void doInternal() { }
    }

    // Option 3: Separate files (always works, cleanest)
    // Put each top-level class in its own .java file

    public static void main(String[] args) {
        System.out.println("=== Single Top-Level Class ===\n");

        System.out.println("RULE: One top-level class per source file");
        System.out.println();
        System.out.println("Multiple top-level classes in one file:");
        System.out.println("1. Can cause compile-order-dependent behavior");
        System.out.println("2. Confusing - which class is the 'main' one?");
        System.out.println("3. Some IDEs don't handle it well");
        System.out.println();
        System.out.println("INSTEAD use:");
        System.out.println("- Static member classes (for helpers)");
        System.out.println("- Separate files (always safe)");
    }
}
