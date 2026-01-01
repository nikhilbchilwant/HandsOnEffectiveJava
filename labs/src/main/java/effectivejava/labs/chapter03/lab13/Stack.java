package effectivejava.labs.chapter03.lab13;

import java.util.Arrays;

/**
 * ============================================================================
 * LAB 13: Override clone Judiciously (Item 13)
 * ============================================================================
 * Chapter 3, pp. 58-66
 * 
 * SCENARIO:
 * Stack implements Cloneable but has a BUG: cloned Stack shares the 
 * same array with the original! Modifying one affects the other.
 * 
 * THE BUG:
 * Default clone() creates shallow copy — reference fields point to same objects.
 * 
 * YOUR TASK:
 * TODO #1: Deep copy the elements array in clone()
 * TODO #2: BETTER: Consider copy constructor or factory instead
 * ============================================================================
 */
public class Stack implements Cloneable {

    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 16;

    public Stack() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop() {
        if (size == 0) throw new IllegalStateException("Empty stack");
        Object result = elements[--size];
        elements[size] = null;
        return result;
    }

    public int size() { return size; }

    private void ensureCapacity() {
        if (elements.length == size)
            elements = Arrays.copyOf(elements, 2 * size + 1);
    }

    // =========================================================================
    // FIXME: Shallow clone shares the same array!
    // =========================================================================
    
    @Override
    public Stack clone() {
        try {
            Stack result = (Stack) super.clone();
            // PROBLEM: result.elements points to same array as this.elements!
            // Pushing to one stack affects the other!
            
            // TODO: Deep copy the array:
            // result.elements = elements.clone();
            
            return result;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();  // Can't happen
        }
    }

    // =========================================================================
    // BETTER ALTERNATIVE: Copy constructor
    // =========================================================================
    
    // public Stack(Stack original) {
    //     this.elements = original.elements.clone();
    //     this.size = original.size;
    // }
    //
    // // Or static factory:
    // public static Stack copyOf(Stack original) {
    //     return new Stack(original);
    // }

    public static void main(String[] args) {
        System.out.println("=== Clone Problem Demo ===\n");

        Stack original = new Stack();
        original.push("A");
        original.push("B");
        original.push("C");

        Stack cloned = original.clone();

        System.out.println("Original size: " + original.size());
        System.out.println("Cloned size: " + cloned.size());

        // Modify the clone
        System.out.println("\nPushing 'D' to cloned stack...");
        cloned.push("D");

        System.out.println("Original size: " + original.size());
        System.out.println("Cloned size: " + cloned.size());

        if (original.size() > 3) {
            System.out.println("\nBUG! Modifying clone affected original!");
            System.out.println("Cause: Shallow clone shares the array.");
            System.out.println("Fix: Deep copy elements array in clone().");
        } else {
            System.out.println("\nClone is independent — fix successful!");
        }
    }
}
