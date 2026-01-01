package effectivejava.labs.chapter02.lab07;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * ============================================================================
 * LAB 07: Eliminate Obsolete Object References (Item 7)
 * ============================================================================
 * Chapter 2, pp. 26-29
 * 
 * SCENARIO:
 * A stack implementation has a MEMORY LEAK! When you pop() elements,
 * the array still holds references to them, preventing garbage collection.
 * 
 * THE BUG:
 * After popping, the array slot still contains the old object reference.
 * Even though it's "logically" empty, it's an "obsolete reference" that
 * prevents GC.
 * 
 * YOUR TASK:
 * TODO: In pop(), null out the obsolete reference after returning it
 * ============================================================================
 */
public class LeakyStack {

    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 16;

    public LeakyStack() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    // =========================================================================
    // FIXME: This pop() leaks memory!
    // =========================================================================
    
    public Object pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        // PROBLEM: elements[size] still holds the reference!
        // Even though we've "popped" it, GC can't reclaim it.
        return elements[--size];
        
        // TODO: Fix by nulling out the obsolete reference:
        // Object result = elements[--size];
        // elements[size] = null;  // Eliminate obsolete reference!
        // return result;
    }

    public int size() {
        return size;
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }

    // Expose internal array for demonstration
    Object[] getInternalArray() {
        return elements;
    }

    public static void main(String[] args) {
        System.out.println("=== Memory Leak Demonstration ===\n");

        LeakyStack stack = new LeakyStack();

        // Push some objects
        System.out.println("Pushing elements...");
        for (int i = 0; i < 5; i++) {
            stack.push("Element-" + i);
        }
        System.out.println("Stack size: " + stack.size());

        // Pop all elements
        System.out.println("\nPopping all elements...");
        while (stack.size() > 0) {
            System.out.println("Popped: " + stack.pop());
        }
        System.out.println("Stack size: " + stack.size());

        // Check the internal array - obsolete references still there!
        System.out.println("\n--- Internal Array State ---");
        Object[] internal = stack.getInternalArray();
        for (int i = 0; i < 10; i++) {
            System.out.println("elements[" + i + "] = " + internal[i]);
        }

        System.out.println("\nPROBLEM: Popped objects still referenced!");
        System.out.println("They can't be garbage collected - MEMORY LEAK!");
        System.out.println("\nFIX: Null out elements[size] after pop()");
    }
}
