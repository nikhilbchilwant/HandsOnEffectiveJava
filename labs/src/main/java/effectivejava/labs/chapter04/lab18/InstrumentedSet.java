package effectivejava.labs.chapter04.lab18;

import java.util.*;

/**
 * ============================================================================
 * LAB 18 (Part C): Composition-Based InstrumentedSet (Item 18)
 * ============================================================================
 * Chapter 4, pp. 90-91
 * 
 * SCENARIO:
 * Now that you have ForwardingSet, create InstrumentedSet using COMPOSITION.
 * This version WORKS correctly, unlike the inheritance-based InstrumentedHashSet.
 * 
 * YOUR TASK:
 * TODO #1: Extend ForwardingSet<E> (not HashSet!)
 * TODO #2: Add a counter field for tracking additions
 * TODO #3: Override add() to increment counter and delegate
 * TODO #4: Override addAll() to increment counter and delegate
 * TODO #5: Add getAddCount() method
 * 
 * KEY INSIGHT:
 * Unlike InstrumentedHashSet, this works because:
 * - ForwardingSet.addAll() delegates to the WRAPPED set's addAll()
 * - The wrapped set doesn't call OUR add() method
 * - No double-counting!
 * 
 * WRAPPER PATTERN BENEFITS:
 * - Works with ANY Set implementation (HashSet, TreeSet, etc.)
 * - No fragile base class coupling
 * - Can wrap sets returned by other code
 * ============================================================================
 */
public class InstrumentedSet<E> extends ForwardingSet<E> {
    
    // =========================================================================
    // TODO #2: Add counter field
    // =========================================================================
    
    // private int addCount = 0;

    // =========================================================================
    // TODO #1: Constructor taking any Set<E>
    // =========================================================================
    
    public InstrumentedSet(Set<E> s) {
        super(s);
    }

    // =========================================================================
    // TODO #3: Override add() - increment and delegate
    // =========================================================================
    
    @Override 
    public boolean add(E e) {
        // FIXME: Increment addCount, then call super.add(e)
        return super.add(e);  // TODO: Add counting
    }

    // =========================================================================
    // TODO #4: Override addAll() - increment by collection size
    // =========================================================================
    
    @Override 
    public boolean addAll(Collection<? extends E> c) {
        // FIXME: Increment addCount by c.size(), then delegate
        // KEY: super.addAll() goes to ForwardingSet which calls wrapped set's addAll()
        // NOT our add() method - so no double counting!
        return super.addAll(c);  // TODO: Add counting
    }

    // =========================================================================
    // TODO #5: Getter for the count
    // =========================================================================
    
    public int getAddCount() {
        // TODO: return addCount;
        return 0;
    }

    // =========================================================================
    // Demo comparing both approaches
    // =========================================================================
    
    public static void main(String[] args) {
        System.out.println("=== Composition vs Inheritance Demo ===\n");

        // BROKEN: Inheritance-based (InstrumentedHashSet)
        System.out.println("--- InstrumentedHashSet (BROKEN - inheritance) ---");
        InstrumentedHashSet<String> broken = new InstrumentedHashSet<>();
        broken.addAll(List.of("A", "B", "C"));
        System.out.println("Added 3 elements");
        System.out.println("getAddCount() = " + broken.getAddCount());
        System.out.println("Expected: 3, Got: " + broken.getAddCount() + 
                          (broken.getAddCount() == 3 ? " ✓" : " ✗ (double-counted!)"));

        // FIXED: Composition-based (InstrumentedSet)
        System.out.println("\n--- InstrumentedSet (FIXED - composition) ---");
        InstrumentedSet<String> fixed = new InstrumentedSet<>(new HashSet<>());
        fixed.addAll(List.of("A", "B", "C"));
        System.out.println("Added 3 elements");
        System.out.println("getAddCount() = " + fixed.getAddCount());
        System.out.println("Expected: 3, Got: " + fixed.getAddCount() + 
                          (fixed.getAddCount() == 3 ? " ✓" : " ✗ (TODO: implement)"));

        System.out.println("\n--- Wrapper Flexibility ---");
        System.out.println("// Works with ANY Set implementation:");
        System.out.println("new InstrumentedSet<>(new TreeSet<>());");
        System.out.println("new InstrumentedSet<>(new LinkedHashSet<>());");
        System.out.println("new InstrumentedSet<>(existingSet);  // wrap existing");
    }
}
