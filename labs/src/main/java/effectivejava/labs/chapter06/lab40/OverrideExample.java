package effectivejava.labs.chapter06.lab40;

import java.util.*;

/**
 * ============================================================================
 * LAB 40: Consistently Use the Override Annotation (Item 40)
 * ============================================================================
 * Chapter 6, pp. 188-190
 * 
 * SCENARIO:
 * Methods intended to override parents are missing @Override.
 * Without it, typos silently create NEW methods instead of overriding.
 * 
 * YOUR TASK:
 * TODO: Add @Override to all overriding methods
 * ============================================================================
 */
public class OverrideExample {

    static class Bigram {
        private final char first;
        private final char second;

        public Bigram(char first, char second) {
            this.first = first;
            this.second = second;
        }

        // =====================================================================
        // BUG: Missing @Override! This doesn't override Object.equals!
        // =====================================================================
        
        // FIXME: Parameter is wrong type - this is OVERLOAD, not override!
        public boolean equals(Bigram b) {  // Should be (Object o)!
            return b.first == first && b.second == second;
        }

        // TODO: Add @Override and fix parameter:
        // @Override
        // public boolean equals(Object o) {
        //     if (!(o instanceof Bigram)) return false;
        //     Bigram b = (Bigram) o;
        //     return b.first == first && b.second == second;
        // }

        public int hashCode() {  // FIXME: Add @Override
            return 31 * first + second;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== @Override Annotation ===\n");

        Set<Bigram> s = new HashSet<>();
        
        for (int i = 0; i < 10; i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                s.add(new Bigram(ch, ch));  // e.g., 'aa', 'bb', etc.
            }
        }

        // Without @Override, we overloaded equals instead of overriding!
        // HashSet uses Object.equals, which checks reference equality
        System.out.println("Set size: " + s.size());
        System.out.println("Expected: 26 (one per letter)");
        System.out.println("Actual: " + s.size() + " (BUG if not 26!)");

        // Test the broken equals
        Bigram b1 = new Bigram('a', 'a');
        Bigram b2 = new Bigram('a', 'a');

        System.out.println("\nb1.equals(b2): " + b1.equals(b2));  // Uses our method
        System.out.println("b1.equals((Object)b2): " + b1.equals((Object)b2));  // Uses Object's!

        System.out.println("\n--- Why @Override Helps ---");
        System.out.println("If we added @Override to equals(Bigram b),");
        System.out.println("compiler would ERROR: method does not override");
        System.out.println("This catches the bug immediately!");
    }
}
