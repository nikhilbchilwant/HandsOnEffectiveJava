package effectivejava.flawed.chapter02.lab07;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * FLAWED IMPLEMENTATION - Stack with memory leak
 * 
 * This stack implementation has a subtle memory leak: when elements are 
 * popped, the underlying array still holds references to them.
 * 
 * The GC cannot collect these "obsolete references" because they're still
 * reachable through the array, even though they're beyond the logical size.
 */
public class LeakyStack<E> {

    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public LeakyStack() {
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(E e) {
        ensureCapacity();
        elements[size++] = e;
    }

    @SuppressWarnings("unchecked")
    public E pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        // MEMORY LEAK: We decrement size but leave the reference in the array!
        // The element at elements[size] is now "obsolete" but still reachable
        return (E) elements[--size];

        // WHAT IT SHOULD DO:
        // E result = (E) elements[--size];
        // elements[size] = null;  // Clear the obsolete reference!
        // return result;
    }

    @SuppressWarnings("unchecked")
    public E peek() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        return (E) elements[size - 1];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }

    /**
     * For debugging: show the internal array state.
     * Demonstrates that "removed" elements are still there.
     */
    public void debugPrintInternals() {
        System.out.println("Size: " + size + ", Capacity: " + elements.length);
        System.out.println("Elements array (showing obsolete references):");
        for (int i = 0; i < elements.length; i++) {
            String marker = i < size ? " [active]" : " [OBSOLETE]";
            if (elements[i] != null) {
                System.out.println("  [" + i + "] = " + elements[i] + marker);
            }
        }
    }
}
