package effectivejava.labs.chapter05.lab26;

import java.util.*;

/**
 * ============================================================================
 * LAB 26: Don't Use Raw Types (Item 26)
 * ============================================================================
 * Chapter 5, pp. 117-123
 * 
 * SCENARIO:
 * Code uses raw types like List instead of List<String>. This loses
 * type safety and can cause ClassCastException at runtime.
 * 
 * YOUR TASK:
 * TODO: Replace all raw types with properly parameterized types
 * ============================================================================
 */
public class RawTypeProblems {

    // =========================================================================
    // FIXME: Raw types lose type safety!
    // =========================================================================
    
    // BAD: Raw type List
    private final List stamps = new ArrayList();  // TODO: List<Stamp>

    public void addStamp(Object stamp) {
        stamps.add(stamp);  // Accepts ANYTHING!
    }

    public void processStamps() {
        for (Object obj : stamps) {
            Stamp stamp = (Stamp) obj;  // ClassCastException if wrong type!
            stamp.cancel();
        }
    }

    // =========================================================================
    // BAD: Raw Iterator
    // =========================================================================
    
    public int countElements(Collection c) {  // TODO: Collection<?>
        int count = 0;
        for (Iterator i = c.iterator(); i.hasNext(); ) {
            Object element = i.next();
            count++;
        }
        return count;
    }

    // =========================================================================
    // When you MUST use raw types (very rare)
    // =========================================================================
    
    // Class literals: List.class not List<String>.class
    // instanceof: if (o instanceof Set) - then cast to Set<?>

    static class Stamp {
        void cancel() { System.out.println("Stamp cancelled"); }
    }

    static class Coin {
        int value() { return 100; }
    }

    public static void main(String[] args) {
        System.out.println("=== Raw Type Problems ===\n");

        RawTypeProblems demo = new RawTypeProblems();

        // PROBLEM: Can add wrong type!
        demo.addStamp(new Stamp());
        demo.addStamp(new Coin());  // Oops! Coin isn't a Stamp!

        System.out.println("Added Stamp and Coin to 'stamps' list");

        try {
            demo.processStamps();  // ClassCastException!
        } catch (ClassCastException e) {
            System.out.println("ClassCastException: " + e.getMessage());
        }

        System.out.println("\n--- Solution ---");
        System.out.println("Use List<Stamp> stamps = new ArrayList<>();");
        System.out.println("Compiler catches wrong type at compile time!");
    }
}
