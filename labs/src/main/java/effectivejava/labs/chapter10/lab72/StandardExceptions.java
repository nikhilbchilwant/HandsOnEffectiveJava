package effectivejava.labs.chapter10.lab72;

import java.util.*;

/**
 * ============================================================================
 * LAB 72: Favor the Use of Standard Exceptions (Item 72)
 * ============================================================================
 * Chapter 10, pp. 300-302
 * 
 * SCENARIO:
 * Code creates custom exceptions when standard ones would work.
 * Reusing standard exceptions makes code more readable and reduces learning curve.
 * 
 * YOUR TASK:
 * TODO: Replace custom exceptions with standard ones
 * ============================================================================
 */
public class StandardExceptions {

    // =========================================================================
    // BAD: Custom exceptions when standard ones exist
    // =========================================================================
    
    // Don't create this:
    // class InvalidArgumentException extends RuntimeException { }
    // Use: IllegalArgumentException

    // Don't create this:
    // class InvalidStateException extends RuntimeException { }
    // Use: IllegalStateException

    // Don't create this:
    // class NotFoundException extends RuntimeException { }
    // Use: NoSuchElementException

    // =========================================================================
    // Examples of proper exception usage
    // =========================================================================
    
    public void setAge(int age) {
        if (age < 0) {
            // GOOD: Use standard IllegalArgumentException
            throw new IllegalArgumentException("Age cannot be negative: " + age);
        }
    }

    public void start() {
        boolean alreadyStarted = true;
        if (alreadyStarted) {
            // GOOD: Use standard IllegalStateException  
            throw new IllegalStateException("Already started");
        }
    }

    public Object getFirst(List<?> list) {
        if (list.isEmpty()) {
            // GOOD: Use standard NoSuchElementException
            throw new NoSuchElementException("Empty list");
        }
        return list.get(0);
    }

    public void accessElement(List<?> list, int index) {
        if (index < 0 || index >= list.size()) {
            // GOOD: Use standard IndexOutOfBoundsException
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + list.size());
        }
    }

    public void requireNonNull(Object obj) {
        if (obj == null) {
            // GOOD: Use Objects.requireNonNull or NullPointerException
            throw new NullPointerException("Object cannot be null");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Standard Exceptions ===\n");

        System.out.println("Common standard exceptions:");
        System.out.println("- IllegalArgumentException: bad parameter value");
        System.out.println("- IllegalStateException: object state is wrong for method");
        System.out.println("- NullPointerException: null where prohibited");
        System.out.println("- IndexOutOfBoundsException: index out of range");
        System.out.println("- NoSuchElementException: nothing to return");
        System.out.println("- UnsupportedOperationException: object doesn't support method");
        System.out.println("- ConcurrentModificationException: illegal concurrent modification");

        StandardExceptions demo = new StandardExceptions();

        try {
            demo.setAge(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("\nCaught: " + e);
        }

        try {
            demo.getFirst(List.of());
        } catch (NoSuchElementException e) {
            System.out.println("Caught: " + e);
        }
    }
}
