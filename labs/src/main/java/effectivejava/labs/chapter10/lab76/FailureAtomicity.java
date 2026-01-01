package effectivejava.labs.chapter10.lab76;

import java.util.*;

/**
 * ============================================================================
 * LAB 76: Strive for Failure Atomicity (Item 76)
 * ============================================================================
 * Chapter 10, pp. 308-310
 * 
 * SCENARIO:
 * A method that fails leaves the object in an inconsistent state.
 * Failure atomicity means: if a method fails, the object should stay
 * in the state it was in before the call.
 * 
 * YOUR TASK:
 * TODO: Reorder operations to achieve failure atomicity
 * ============================================================================
 */
public class FailureAtomicity {

    // =========================================================================
    // BAD: Modifies state before validation - not atomic!
    // =========================================================================

    static class StackBad {
        private Object[] elements = new Object[16];
        private int size = 0;

        public Object pop() {
            size--;  // Modifies state first!
            if (size < 0) {
                throw new EmptyStackException();  // State is now corrupted!
            }
            return elements[size];
        }
    }

    // =========================================================================
    // GOOD: Validate before modifying - failure atomic
    // =========================================================================

    static class StackGood {
        private Object[] elements = new Object[16];
        private int size = 0;

        public void push(Object e) {
            ensureCapacity();
            elements[size++] = e;
        }

        public Object pop() {
            if (size == 0) {
                throw new EmptyStackException();  // Fail before any change
            }
            Object result = elements[--size];
            elements[size] = null;
            return result;
        }

        private void ensureCapacity() {
            if (elements.length == size) {
                elements = Arrays.copyOf(elements, 2 * size + 1);
            }
        }
    }

    // =========================================================================
    // Patterns for failure atomicity
    // =========================================================================

    static class Account {
        private int balance;

        public Account(int balance) {
            this.balance = balance;
        }

        // Pattern 1: Check preconditions first
        public void withdraw(int amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be positive");
            }
            if (amount > balance) {
                throw new IllegalStateException("Insufficient funds");
            }
            // Only modify after all checks pass
            balance -= amount;
        }

        // Pattern 2: Operate on a copy, then swap
        public void complexOperation(int[] newRates) {
            // Work on a copy
            int tempBalance = balance;
            for (int rate : newRates) {
                tempBalance = tempBalance * rate / 100;
                if (tempBalance < 0) {
                    throw new ArithmeticException("Went negative");
                }
            }
            // Only update if all succeeded
            balance = tempBalance;
        }

        public int getBalance() { return balance; }
    }

    public static void main(String[] args) {
        System.out.println("=== Failure Atomicity ===\n");

        StackGood stack = new StackGood();
        stack.push("A");
        stack.push("B");

        System.out.println("Popped: " + stack.pop());
        System.out.println("Popped: " + stack.pop());

        try {
            stack.pop();  // Should fail atomically
        } catch (EmptyStackException e) {
            System.out.println("Exception on empty - stack still valid!");
        }

        System.out.println("\n--- Patterns ---");
        System.out.println("1. Check preconditions before modifying");
        System.out.println("2. Order operations: reads before writes");
        System.out.println("3. Operate on a copy, then swap");
        System.out.println("4. Recovery code to restore state (last resort)");
    }
}
