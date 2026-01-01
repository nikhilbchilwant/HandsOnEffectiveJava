package effectivejava.labs.chapter04.lab18;

import java.util.*;

/**
 * ============================================================================
 * LAB 18 (Part B): Reusable Forwarding Class (Item 18)
 * ============================================================================
 * Chapter 4, p. 90
 * 
 * SCENARIO:
 * After seeing how InstrumentedHashSet fails, Bloch's solution is:
 * 1. Create a reusable ForwardingSet that delegates all methods
 * 2. Extend ForwardingSet and override only what you need
 * 
 * YOUR TASK:
 * TODO #1: Store the wrapped Set in a private final field
 * TODO #2: Implement all Set methods by delegating to the wrapped set
 * TODO #3: This class should implement Set<E>
 * 
 * This "Decorator" pattern provides:
 * - Composition over inheritance
 * - Works with any Set implementation
 * - Only override what you need in subclass
 * ============================================================================
 */
public class ForwardingSet<E> implements Set<E> {
    
    // =========================================================================
    // TODO #1: Store the delegate Set and implement constructor
    // =========================================================================
    
    // FIXME: Uncomment and use this field for delegation
    // private final Set<E> s;
    
    // Placeholder constructor (replace with proper implementation)
    public ForwardingSet(Set<E> s) {
        // TODO: Store s in a field: this.s = Objects.requireNonNull(s);
        // Then update all methods below to delegate to s
    }

    // =========================================================================
    // TODO #2: Delegate ALL Set methods to the wrapped set
    // =========================================================================

    @Override
    public int size() { 
        // TODO: return s.size();
        throw new UnsupportedOperationException("TODO"); 
    }
    
    @Override
    public boolean isEmpty() { 
        // TODO: return s.isEmpty();
        throw new UnsupportedOperationException("TODO"); 
    }
    
    @Override
    public boolean contains(Object o) { 
        // TODO: return s.contains(o);
        throw new UnsupportedOperationException("TODO"); 
    }
    
    @Override
    public Iterator<E> iterator() { 
        // TODO: return s.iterator();
        throw new UnsupportedOperationException("TODO"); 
    }
    
    @Override
    public Object[] toArray() { 
        // TODO: return s.toArray();
        throw new UnsupportedOperationException("TODO"); 
    }
    
    @Override
    public <T> T[] toArray(T[] a) { 
        // TODO: return s.toArray(a);
        throw new UnsupportedOperationException("TODO"); 
    }
    
    @Override
    public boolean add(E e) { 
        // TODO: return s.add(e);
        throw new UnsupportedOperationException("TODO"); 
    }
    
    @Override
    public boolean remove(Object o) { 
        // TODO: return s.remove(o);
        throw new UnsupportedOperationException("TODO"); 
    }
    
    @Override
    public boolean containsAll(Collection<?> c) { 
        // TODO: return s.containsAll(c);
        throw new UnsupportedOperationException("TODO"); 
    }
    
    @Override
    public boolean addAll(Collection<? extends E> c) { 
        // TODO: return s.addAll(c);
        throw new UnsupportedOperationException("TODO"); 
    }
    
    @Override
    public boolean retainAll(Collection<?> c) { 
        // TODO: return s.retainAll(c);
        throw new UnsupportedOperationException("TODO"); 
    }
    
    @Override
    public boolean removeAll(Collection<?> c) { 
        // TODO: return s.removeAll(c);
        throw new UnsupportedOperationException("TODO"); 
    }
    
    @Override
    public void clear() { 
        // TODO: s.clear();
        throw new UnsupportedOperationException("TODO"); 
    }

    @Override
    public boolean equals(Object o) { 
        // TODO: return s.equals(o);
        throw new UnsupportedOperationException("TODO"); 
    }
    
    @Override
    public int hashCode() { 
        // TODO: return s.hashCode();
        throw new UnsupportedOperationException("TODO"); 
    }
    
    @Override
    public String toString() { 
        // TODO: return s.toString();
        throw new UnsupportedOperationException("TODO"); 
    }
}
