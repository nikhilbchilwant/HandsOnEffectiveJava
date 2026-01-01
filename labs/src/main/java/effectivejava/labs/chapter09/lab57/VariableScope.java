package effectivejava.labs.chapter09.lab57;

import java.util.*;

/**
 * ============================================================================
 * LAB 57: Minimize the Scope of Local Variables (Item 57)
 * ============================================================================
 * Chapter 9, pp. 261-264
 * 
 * SCENARIO:
 * Variables are declared far from their first use, making code harder
 * to follow and prone to reuse errors.
 * 
 * YOUR TASK:
 * TODO #1: Declare variables where first used
 * TODO #2: Prefer for loops to while (for tighter scope)
 * TODO #3: Keep methods small (helps with scoping)
 * ============================================================================
 */
public class VariableScope {

    // =========================================================================
    // BAD: Variable declared far from use
    // =========================================================================
    
    public void badScopeExample(List<String> items) {
        // FIXME: 'result' declared way before it's needed
        StringBuilder result = new StringBuilder();  // Declared too early!
        
        int x = 0;  // Why here?
        int y = 0;  // Not needed until later!
        
        // ... many lines of unrelated code ...
        for (String item : items) {
            System.out.println("Processing: " + item);
        }
        // ... more code ...
        
        // Finally using result, 50 lines later
        for (String item : items) {
            result.append(item).append(",");
        }
        
        x = items.size();  // Finally using x
        y = x * 2;
    }

    // =========================================================================
    // BAD: While loop leaves variable in wider scope
    // =========================================================================
    
    public void whileVsFor(List<String> list) {
        // BAD: Iterator accessible after loop
        Iterator<String> i = list.iterator();
        while (i.hasNext()) {
            System.out.println(i.next());
        }
        // Bug: Can accidentally reuse 'i' here!

        // BETTER: For loop contains iterator in loop scope
        // for (Iterator<String> i = list.iterator(); i.hasNext(); ) {
        //     System.out.println(i.next());
        // }
        // i is out of scope here - can't accidentally reuse
    }

    // =========================================================================
    // TODO: Refactored version
    // =========================================================================
    
    // public void goodScopeExample(List<String> items) {
    //     for (String item : items) {
    //         System.out.println("Processing: " + item);
    //     }
    //     
    //     // Declare where first used
    //     StringBuilder result = new StringBuilder();
    //     for (String item : items) {
    //         result.append(item).append(",");
    //     }
    //     
    //     int x = items.size();  // Declared and initialized together
    //     int y = x * 2;
    // }

    public static void main(String[] args) {
        System.out.println("=== Variable Scope ===\n");

        System.out.println("BAD practice:");
        System.out.println("- Declare variables at method start");
        System.out.println("- Use while loops with external iterator");
        System.out.println();
        System.out.println("GOOD practice:");
        System.out.println("- Declare where first used");
        System.out.println("- Use for-each or for loop");
        System.out.println("- Keep methods small");
    }
}
