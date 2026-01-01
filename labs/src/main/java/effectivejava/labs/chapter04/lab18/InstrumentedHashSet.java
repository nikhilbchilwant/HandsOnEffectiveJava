package effectivejava.labs.chapter04.lab18;

import java.util.Collection;
import java.util.HashSet;

/**
 * ============================================================================
 * LAB 18: Favor Composition Over Inheritance (Item 18)
 * ============================================================================
 * 
 * SCENARIO:
 * You want to count how many elements have been added to a HashSet.
 * This implementation extends HashSet, but has a BUG: the count is WRONG!
 * 
 * THE BUG:
 * HashSet.addAll() internally calls add() for each element.
 * So when you add 3 elements via addAll:
 *   1. Our addAll() increments count by 3
 *   2. HashSet.addAll() calls add() 3 times, incrementing count by 3 more
 *   3. Final count: 6 instead of 3!
 * 
 * THE FUNDAMENTAL PROBLEM:
 * We're depending on HashSet's implementation detail (that addAll calls add).
 * This is NOT part of HashSet's contract - it could change!
 * 
 * YOUR TASK:
 * TODO: Refactor using the DECORATOR pattern (composition):
 * 
 * 1. Create a ForwardingSet<E> class that:
 *    - Implements Set<E>
 *    - Has a private Set<E> field (the wrapped set)
 *    - Forwards all methods to the wrapped set
 * 
 * 2. Create InstrumentedSet<E> that extends ForwardingSet<E>:
 *    - Overrides only add() and addAll()
 *    - Counts correctly regardless of wrapped set's implementation!
 * 
 * BENEFITS OF COMPOSITION:
 * - Works with ANY Set implementation (HashSet, TreeSet, etc.)
 * - Doesn't depend on implementation details
 * - Can be stacked: InstrumentedSet<SynchronizedSet<HashSet>>
 * 
 * VALIDATION:
 * Run main() - count should equal number of elements
 * ============================================================================
 */
public class InstrumentedHashSet<E> extends HashSet<E> {

    private int addCount = 0;

    public InstrumentedHashSet() {
    }

    public InstrumentedHashSet(int initCap, float loadFactor) {
        super(initCap, loadFactor);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    // =========================================================================
    // FIXME: HashSet.addAll() calls add() for each element!
    // This causes double-counting!
    // =========================================================================
    
    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);  // This calls add() for each element!
    }

    public int getAddCount() {
        return addCount;
    }

    // =========================================================================
    // TODO: Create composition-based solution
    // =========================================================================
    
    // Step 1: Create ForwardingSet (in a separate file or inner class):
    //
    // public class ForwardingSet<E> implements Set<E> {
    //     private final Set<E> s;
    //     public ForwardingSet(Set<E> s) { this.s = s; }
    //     
    //     // Forward all Set methods:
    //     public boolean add(E e) { return s.add(e); }
    //     public boolean addAll(Collection<? extends E> c) { return s.addAll(c); }
    //     // ... all other Set methods
    // }
    //
    // Step 2: Create InstrumentedSet:
    //
    // public class InstrumentedSet<E> extends ForwardingSet<E> {
    //     private int addCount = 0;
    //     
    //     public InstrumentedSet(Set<E> s) { super(s); }
    //     
    //     @Override
    //     public boolean add(E e) {
    //         addCount++;
    //         return super.add(e);
    //     }
    //     
    //     @Override
    //     public boolean addAll(Collection<? extends E> c) {
    //         addCount += c.size();
    //         return super.addAll(c);  // Calls FORWARDING addAll, not add()!
    //     }
    // }

    public static void main(String[] args) {
        InstrumentedHashSet<String> s = new InstrumentedHashSet<>();
        
        System.out.println("Adding 3 elements via addAll...");
        s.addAll(java.util.List.of("Snap", "Crackle", "Pop"));
        
        System.out.println("Expected count: 3");
        System.out.println("Actual count: " + s.getAddCount());
        System.out.println("Bug exists? " + (s.getAddCount() != 3));
    }
}
