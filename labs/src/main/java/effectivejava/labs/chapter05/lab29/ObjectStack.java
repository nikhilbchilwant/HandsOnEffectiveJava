package effectivejava.labs.chapter05.lab29;

import java.util.*;

/**
 * ============================================================================
 * LAB 29: Favor Generic Types (Item 29)
 * ============================================================================
 * Chapter 5, pp. 130-135
 * 
 * SCENARIO:
 * A Stack class uses Object instead of generics. This forces callers
 * to cast on every pop(), risking ClassCastException.
 * 
 * YOUR TASK:
 * TODO: Convert to a generic class Stack<E>
 * ============================================================================
 */
public class ObjectStack {

    private Object[] elements;  // TODO: Use E[]
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 16;

    public ObjectStack() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    public void push(Object e) {  // TODO: push(E e)
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop() {  // TODO: return E
        if (size == 0) throw new EmptyStackException();
        Object result = elements[--size];
        elements[size] = null;
        return result;
    }

    public boolean isEmpty() { return size == 0; }

    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }

    // =========================================================================
    // TODO: Convert to generic:
    // =========================================================================
    
    // public class Stack<E> {
    //     private E[] elements;
    //     
    //     @SuppressWarnings("unchecked")
    //     public Stack() {
    //         // Can't create E[], so create Object[] and cast
    //         elements = (E[]) new Object[DEFAULT_CAPACITY];
    //     }
    //     
    //     public void push(E e) {
    //         ensureCapacity();
    //         elements[size++] = e;
    //     }
    //     
    //     public E pop() {
    //         if (size == 0) throw new EmptyStackException();
    //         E result = elements[--size];
    //         elements[size] = null;
    //         return result;
    //     }
    // }

    public static void main(String[] args) {
        System.out.println("=== Object-based Stack ===\n");

        ObjectStack stack = new ObjectStack();
        stack.push("Hello");
        stack.push("World");

        // PROBLEM: Must cast on every pop!
        String s = (String) stack.pop();  // Cast required
        System.out.println("Popped: " + s);

        // Can accidentally push wrong type
        stack.push(123);  // No compile error!
        // String oops = (String) stack.pop();  // ClassCastException!

        System.out.println("\n--- With Generics ---");
        System.out.println("Stack<String> stack = new Stack<>();");
        System.out.println("stack.push(\"Hello\");");
        System.out.println("String s = stack.pop();  // No cast!");
        System.out.println("stack.push(123);  // Compile error!");
    }
}
