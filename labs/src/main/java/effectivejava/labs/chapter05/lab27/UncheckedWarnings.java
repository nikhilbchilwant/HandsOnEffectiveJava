package effectivejava.labs.chapter05.lab27;

import java.util.*;

/**
 * ============================================================================
 * LAB 27: Eliminate Unchecked Warnings (Item 27)
 * ============================================================================
 * Chapter 5, pp. 123-126
 * 
 * SCENARIO:
 * Code has unchecked warnings that are being ignored. Some are easy fixes,
 * others require @SuppressWarnings with a justifying comment.
 * 
 * YOUR TASK:
 * TODO #1: Fix warnings that can be fixed
 * TODO #2: Use @SuppressWarnings on the smallest scope possible
 * TODO #3: Add comment explaining why the suppression is safe
 * ============================================================================
 */
public class UncheckedWarnings {

    // =========================================================================
    // WARNING 1: Raw type usage
    // =========================================================================

    public void rawTypeWarning() {
        // FIXME: Raw type List - add type parameter
        List items = new ArrayList();
        items.add("hello");
        
        // TODO: Fix to:
        // List<String> items = new ArrayList<>();
    }

    // =========================================================================
    // WARNING 2: Unchecked cast
    // =========================================================================

    public <T> T[] toArray(List<T> list, T[] template) {
        // FIXME: Unchecked cast warning
        // This is unavoidable due to generics erasure
        // Must suppress with justification
        
        Object[] result = new Object[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        
        // TODO: Add suppression on smallest scope:
        // @SuppressWarnings("unchecked")  // Safe: we only put T elements in
        // T[] typedResult = (T[]) result;
        // return typedResult;
        
        return (T[]) result;  // Warning here!
    }

    // =========================================================================
    // WARNING 3: Unchecked generic array creation
    // =========================================================================

    @SafeVarargs  // TODO: Add this annotation for varargs of generic type
    public final <T> List<T> asList(T... elements) {
        List<T> result = new ArrayList<>();
        for (T element : elements) {
            result.add(element);
        }
        return result;
    }

    // =========================================================================
    // GOOD EXAMPLE: Properly suppressed with comment
    // =========================================================================

    public <E> E[] copyToArray(Collection<E> collection) {
        Object[] elements = collection.toArray();
        
        // Safe because toArray returns Object[] containing only E elements
        @SuppressWarnings("unchecked")
        E[] result = (E[]) elements;  // Suppression on smallest scope
        
        return result;
    }

    public static void main(String[] args) {
        System.out.println("=== Unchecked Warnings ===\n");

        System.out.println("Compile with: javac -Xlint:unchecked ...");
        System.out.println();
        System.out.println("Rules:");
        System.out.println("1. Eliminate every unchecked warning you CAN");
        System.out.println("2. If you can't, prove cast is safe, then suppress");
        System.out.println("3. Suppress on smallest possible scope");
        System.out.println("4. Always add comment explaining why it's safe");
    }
}
