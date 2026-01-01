package effectivejava.labs.chapter09.lab58;

import java.util.*;

/**
 * ============================================================================
 * LAB 58: Prefer for-each Loops to Traditional for Loops (Item 58)
 * ============================================================================
 * Chapter 9, pp. 264-267
 * 
 * SCENARIO:
 * Traditional for loops with indexes or iterators are error-prone.
 * For-each (enhanced for) loops are cleaner and safer.
 * 
 * YOUR TASK:
 * TODO: Replace traditional loops with for-each where possible
 * ============================================================================
 */
public class ForEachLoops {

    // =========================================================================
    // BAD: Traditional for loop with index
    // =========================================================================

    public void traditionalIndexLoop(List<String> list) {
        // Verbose, error-prone (off-by-one, wrong variable)
        for (int i = 0; i < list.size(); i++) {
            String item = list.get(i);
            System.out.println(item);
        }
    }

    // =========================================================================
    // BAD: Traditional for loop with iterator
    // =========================================================================

    public void traditionalIteratorLoop(Collection<String> c) {
        // Also verbose, iterator variable can be misused
        for (Iterator<String> i = c.iterator(); i.hasNext(); ) {
            String item = i.next();
            System.out.println(item);
        }
    }

    // =========================================================================
    // GOOD: For-each loop
    // =========================================================================

    public void enhancedForLoop(Collection<String> c) {
        // Clean, no index/iterator to get wrong
        for (String item : c) {
            System.out.println(item);
        }
    }

    // =========================================================================
    // When you CAN'T use for-each
    // =========================================================================

    // 1. Filtering - need iterator.remove()
    public void filterList(List<String> list) {
        for (Iterator<String> i = list.iterator(); i.hasNext(); ) {
            if (i.next().startsWith("x")) {
                i.remove();  // Can't do this with for-each
            }
        }
        // In Java 8+: list.removeIf(s -> s.startsWith("x"));
    }

    // 2. Transforming - need list.set(i, newValue)
    public void transformList(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i).toUpperCase());
        }
        // In Java 8+: list.replaceAll(String::toUpperCase);
    }

    // 3. Parallel iteration - need index for coordination
    public void parallelIteration(List<String> a, List<String> b) {
        for (int i = 0; i < Math.min(a.size(), b.size()); i++) {
            System.out.println(a.get(i) + " - " + b.get(i));
        }
    }

    public static void main(String[] args) {
        System.out.println("=== For-each Loops ===\n");

        ForEachLoops demo = new ForEachLoops();
        List<String> items = new ArrayList<>(List.of("a", "b", "c"));

        System.out.println("For-each loop:");
        demo.enhancedForLoop(items);

        System.out.println("\n--- When to use traditional for ---");
        System.out.println("1. Filtering: need iterator.remove()");
        System.out.println("2. Transforming: need list.set()");
        System.out.println("3. Parallel iteration: need index");
        System.out.println("\n(But consider Java 8+ alternatives like removeIf)");
    }
}
