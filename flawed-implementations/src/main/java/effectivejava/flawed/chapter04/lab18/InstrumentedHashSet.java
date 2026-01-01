package effectivejava.flawed.chapter04.lab18;

import java.util.Collection;
import java.util.HashSet;

/**
 * FLAWED IMPLEMENTATION - Extends HashSet to count additions
 * 
 * This implementation incorrectly uses inheritance to add counting behavior.
 * The count will be WRONG because of HashSet's self-use: addAll() calls add()!
 * 
 * The problem:
 *   s.addAll(List.of("a", "b", "c"))
 *   - HashSet.addAll() iterates and calls add() for each element
 *   - Our addAll() increments count by 3
 *   - Each add() call ALSO increments count (+3 more)
 *   - Final count: 6 instead of 3!
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

    /**
     * PROBLEM: HashSet.addAll() calls add() for each element!
     * 
     * So adding 3 elements:
     * 1. addAll increments addCount by 3 (below)
     * 2. super.addAll() calls add() 3 times (above), adding 3 more
     * 3. Total: 6 instead of expected 3
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }

    // Why is this fragile?
    // 1. We depend on HashSet's implementation detail (addAll calls add)
    // 2. If HashSet's implementation changes, our count breaks differently
    // 3. This dependency is not documented in HashSet's contract
    // 4. We can't "just not call super.addAll()" - we need the functionality!
    //
    // The fundamental issue: inheritance exposes implementation, not just interface
}
