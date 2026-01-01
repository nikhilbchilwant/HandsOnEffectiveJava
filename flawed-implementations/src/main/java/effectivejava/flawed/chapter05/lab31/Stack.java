package effectivejava.flawed.chapter05.lab31;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EmptyStackException;
import java.util.List;

/**
 * FLAWED IMPLEMENTATION - Generic stack without bounded wildcards
 * 
 * This stack is too restrictive in its type parameters, rejecting
 * valid operations that should be allowed.
 */
public class Stack<E> {

    private final List<E> elements = new ArrayList<>();

    public void push(E e) {
        elements.add(e);
    }

    public E pop() {
        if (elements.isEmpty()) {
            throw new EmptyStackException();
        }
        return elements.remove(elements.size() - 1);
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public int size() {
        return elements.size();
    }

    /**
     * FLAWED: This won't accept Iterable<Integer> for Stack<Number>!
     * 
     * Stack<Number> numberStack = new Stack<>();
     * Iterable<Integer> integers = List.of(1, 2, 3);
     * numberStack.pushAll(integers);  // COMPILE ERROR!
     * 
     * Because: even though Integer IS-A Number, 
     * Iterable<Integer> IS-NOT-A Iterable<Number> (invariance)
     */
    public void pushAll(Iterable<E> src) {
        for (E e : src) {
            push(e);
        }
    }

    /**
     * FLAWED: This won't accept Collection<Object> for Stack<Number>!
     * 
     * Stack<Number> numberStack = new Stack<>();
     * Collection<Object> objects = new ArrayList<>();
     * numberStack.popAll(objects);  // COMPILE ERROR!
     * 
     * We want to pop Numbers into a collection that can hold Objects,
     * but invariance prevents this.
     */
    public void popAll(Collection<E> dst) {
        while (!isEmpty()) {
            dst.add(pop());
        }
    }

    // Think about:
    // - pushAll: src PRODUCES elements (we read from it)
    // - popAll: dst CONSUMES elements (we write to it)
    //
    // Apply PECS:
    // - Producer → ? extends E
    // - Consumer → ? super E
}
