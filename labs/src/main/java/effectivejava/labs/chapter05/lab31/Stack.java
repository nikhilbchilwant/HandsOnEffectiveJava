package effectivejava.labs.chapter05.lab31;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EmptyStackException;
import java.util.List;

/**
 * ============================================================================
 * LAB 31: Use Bounded Wildcards (PECS) (Item 31)
 * ============================================================================
 * 
 * SCENARIO:
 * This generic stack has pushAll() and popAll() methods that are too 
 * restrictive in their type parameters. Valid operations are rejected!
 * 
 * THE PROBLEM:
 *   Stack<Number> numberStack = new Stack<>();
 *   Iterable<Integer> integers = List.of(1, 2, 3);
 *   numberStack.pushAll(integers);  // COMPILE ERROR! (should work)
 * 
 * Even though Integer IS-A Number, Iterable<Integer> IS-NOT-A Iterable<Number>
 * because generics are INVARIANT.
 * 
 * YOUR TASK:
 * Apply the PECS rule:
 *   Producer Extends - if parameter PROVIDES T values, use <? extends T>
 *   Consumer Super   - if parameter RECEIVES T values, use <? super T>
 * 
 * TODO #1: Fix pushAll signature:
 *          pushAll(Iterable<? extends E> src)
 *          (src PRODUCES E elements for us to consume)
 * 
 * TODO #2: Fix popAll signature:
 *          popAll(Collection<? super E> dst)
 *          (dst CONSUMES E elements that we produce)
 * 
 * VALIDATION:
 * Run main() - all operations should compile and work
 * ============================================================================
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

    // =========================================================================
    // FIXME: This won't accept Iterable<Integer> for Stack<Number>!
    // src is a PRODUCER (we read from it) → use ? extends E
    // =========================================================================
    
    public void pushAll(Iterable<E> src) {  // TODO: Change to Iterable<? extends E>
        for (E e : src) {
            push(e);
        }
    }

    // =========================================================================
    // FIXME: This won't accept Collection<Object> for Stack<Number>!
    // dst is a CONSUMER (we write to it) → use ? super E
    // =========================================================================
    
    public void popAll(Collection<E> dst) {  // TODO: Change to Collection<? super E>
        while (!isEmpty()) {
            dst.add(pop());
        }
    }

    public static void main(String[] args) {
        System.out.println("=== PECS Test ===\n");
        
        Stack<Number> numberStack = new Stack<>();
        
        // TEST 1: pushAll with subtypes
        System.out.println("Test 1: Push integers into Number stack");
        Iterable<Integer> integers = List.of(1, 2, 3);
        
        // TODO: This should compile after fixing pushAll signature
        // numberStack.pushAll(integers);
        // System.out.println("Pushed integers: " + integers);
        
        // For now, use the workaround:
        for (Integer i : integers) {
            numberStack.push(i);
        }
        System.out.println("Stack size: " + numberStack.elements.size());
        
        // TEST 2: popAll into supertype collection
        System.out.println("\nTest 2: Pop numbers into Object collection");
        Collection<Object> objects = new ArrayList<>();
        
        // TODO: This should compile after fixing popAll signature
        // numberStack.popAll(objects);
        // System.out.println("Objects: " + objects);
        
        // For now, use the workaround:
        while (!numberStack.isEmpty()) {
            objects.add(numberStack.pop());
        }
        System.out.println("Objects: " + objects);
        
        System.out.println("\n=== After fixing, uncomment the PECS calls! ===");
    }
}
