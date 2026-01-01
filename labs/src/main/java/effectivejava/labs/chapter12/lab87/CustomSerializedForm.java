package effectivejava.labs.chapter12.lab87;

import java.io.*;
import java.util.*;

/**
 * ============================================================================
 * LAB 87: Consider Using a Custom Serialized Form (Item 87)
 * ============================================================================
 * Chapter 12, pp. 346-352
 * 
 * SCENARIO:
 * Default serialized form captures implementation details, not logical content.
 * This causes bloat, performance issues, and compatibility problems.
 * 
 * YOUR TASK:
 * TODO: Understand when default form is OK vs when to customize
 * ============================================================================
 */
public class CustomSerializedForm {

    // =========================================================================
    // GOOD: Physical representation matches logical
    // =========================================================================

    // Default form is fine here!
    static class Name implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private final String lastName;
        private final String firstName;
        private final String middleName;

        public Name(String last, String first, String middle) {
            lastName = Objects.requireNonNull(last);
            firstName = Objects.requireNonNull(first);
            middleName = middle;  // nullable
        }
        // Physical (3 Strings) = Logical (name with 3 parts)
        // Default serialization is fine!
    }

    // =========================================================================
    // BAD: Physical representation differs from logical
    // =========================================================================

    // Linked list - logical content is just the strings!
    static class StringListBad implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private int size = 0;
        private Entry head = null;

        private static class Entry implements Serializable {
            String data;
            Entry next;
            Entry previous;
        }

        public void add(String s) {
            // Adds to linked list...
            size++;
        }

        // DEFAULT SERIALIZATION IS TERRIBLE:
        // - Serializes all Entry objects with next/previous pointers
        // - Bloated: n entries means 2n extra references serialized
        // - Slow: traverses entire object graph
        // - Breaks if we change to array implementation
    }

    // =========================================================================
    // GOOD: Custom serialized form - just the logical content
    // =========================================================================

    static class StringListGood implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private transient int size = 0;  // transient!
        private transient Entry head = null;  // transient!

        private static class Entry {
            String data;
            Entry next;
            Entry previous;
        }

        public void add(String s) {
            // Adds to linked list...
            size++;
        }

        // Custom serialization: just write the strings
        private void writeObject(ObjectOutputStream s) throws IOException {
            s.defaultWriteObject();  // Write non-transient fields
            s.writeInt(size);
            for (Entry e = head; e != null; e = e.next) {
                s.writeObject(e.data);
            }
        }

        private void readObject(ObjectInputStream s)
                throws IOException, ClassNotFoundException {
            s.defaultReadObject();
            int numElements = s.readInt();
            for (int i = 0; i < numElements; i++) {
                add((String) s.readObject());
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Custom Serialized Form ===\n");

        System.out.println("Use DEFAULT form when:");
        System.out.println("  - Physical representation = Logical content");
        System.out.println("  - Example: Name(first, middle, last)");

        System.out.println("\nUse CUSTOM form when:");
        System.out.println("  - Physical != Logical (e.g., linked list)");
        System.out.println("  - Internal representation may change");
        System.out.println("  - Performance is important");

        System.out.println("\nCustom form pattern:");
        System.out.println("  1. Mark implementation fields transient");
        System.out.println("  2. writeObject: write logical content");
        System.out.println("  3. readObject: reconstruct from logical content");
    }
}
