package effectivejava.labs.chapter08.lab54;

import java.util.*;

/**
 * ============================================================================
 * LAB 54: Return Empty Collections or Arrays, Not Nulls (Item 54)
 * ============================================================================
 * Chapter 8, pp. 247-249
 * 
 * SCENARIO:
 * Methods return null for "no results" instead of empty collections.
 * This forces null checks at every call site.
 * 
 * YOUR TASK:
 * TODO: Return empty collections instead of null
 * ============================================================================
 */
public class ReturnNullProblem {

    private final List<String> items = new ArrayList<>();

    // =========================================================================
    // BAD: Returning null (forces null checks everywhere!)
    // =========================================================================
    
    public List<String> getItemsBad() {
        if (items.isEmpty()) {
            return null;  // PROBLEM: Callers must check for null!
        }
        return new ArrayList<>(items);
    }

    // =========================================================================
    // GOOD: Return empty collection
    // =========================================================================
    
    public List<String> getItemsGood() {
        // Return empty list, not null!
        // Use Collections.emptyList() for immutable empty
        return items.isEmpty() 
            ? Collections.emptyList() 
            : new ArrayList<>(items);
    }

    // For arrays:
    private static final String[] EMPTY_ARRAY = new String[0];

    public String[] getItemsArrayBad() {
        if (items.isEmpty()) return null;  // BAD
        return items.toArray(new String[0]);
    }

    public String[] getItemsArrayGood() {
        // Return empty array, not null
        return items.isEmpty() 
            ? EMPTY_ARRAY  // Reuse empty array constant
            : items.toArray(new String[0]);
    }

    public static void main(String[] args) {
        System.out.println("=== Return Null Problem ===\n");

        ReturnNullProblem demo = new ReturnNullProblem();
        // items is empty

        // BAD: Must check for null
        List<String> bad = demo.getItemsBad();
        if (bad != null) {  // Annoying null check!
            for (String s : bad) {
                System.out.println(s);
            }
        }

        // GOOD: Just iterate (empty list is safe)
        List<String> good = demo.getItemsGood();
        for (String s : good) {  // No null check needed!
            System.out.println(s);
        }

        System.out.println("Empty list size: " + good.size());

        System.out.println("\n--- Why Empty is Better ---");
        System.out.println("1. No null checks at every call site");
        System.out.println("2. Can safely iterate, stream, etc.");
        System.out.println("3. Collections.emptyList() is free (singleton)");
    }
}
