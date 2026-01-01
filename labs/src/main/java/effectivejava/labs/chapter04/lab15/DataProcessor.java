package effectivejava.labs.chapter04.lab15;

import java.util.List;
import java.util.ArrayList;

/**
 * ============================================================================
 * LAB 15: Minimize Accessibility of Classes and Members (Item 15)
 * ============================================================================
 * Chapter 4, pp. 73-78
 * 
 * SCENARIO:
 * A Point class exposes too much — public fields, public inner workings.
 * This breaks encapsulation and makes it impossible to change internals.
 * 
 * YOUR TASK:
 * TODO #1: Make all fields private
 * TODO #2: Reduce method visibility where possible  
 * TODO #3: Make mutable objects defensively copied or returned as immutable
 * ============================================================================
 */
public class DataProcessor {

    // =========================================================================
    // FIXME: Public mutable field! Anyone can corrupt this!
    // =========================================================================
    public List<String> data = new ArrayList<>();
    
    // FIXME: Public array — even final arrays can have elements modified!
    public static final String[] SENSITIVE_VALUES = {"SECRET1", "SECRET2"};

    // FIXME: Should be private — implementation detail
    public int processCount = 0;

    public void addData(String item) {
        data.add(item);
        processCount++;
    }

    public void process() {
        // Process the data
        System.out.println("Processing " + data.size() + " items");
    }

    // =========================================================================
    // TODO: Fix these issues:
    // =========================================================================
    
    // 1. Make fields private:
    //    private final List<String> data = new ArrayList<>();
    //    private int processCount = 0;
    
    // 2. Return defensive copies for mutable collections:
    //    public List<String> getData() {
    //        return List.copyOf(data);  // Immutable copy
    //    }
    
    // 3. For arrays, return copy or immutable list:
    //    private static final String[] SENSITIVE_VALUES = {...};
    //    public static List<String> getSensitiveValues() {
    //        return List.of(SENSITIVE_VALUES);
    //    }
    //    // OR
    //    public static String[] getSensitiveValues() {
    //        return SENSITIVE_VALUES.clone();
    //    }

    public static void main(String[] args) {
        System.out.println("=== Accessibility Problems Demo ===\n");

        DataProcessor processor = new DataProcessor();
        processor.addData("Item1");
        processor.addData("Item2");

        // PROBLEM 1: Direct field access
        System.out.println("Direct field access:");
        processor.data.clear();  // Wiped out all data!
        System.out.println("data.size() after external clear: " + processor.data.size());

        // PROBLEM 2: Public array contents modifiable
        System.out.println("\nPublic array modification:");
        System.out.println("Before: " + DataProcessor.SENSITIVE_VALUES[0]);
        DataProcessor.SENSITIVE_VALUES[0] = "HACKED";
        System.out.println("After: " + DataProcessor.SENSITIVE_VALUES[0]);

        System.out.println("\n--- Solution ---");
        System.out.println("1. Make fields private");
        System.out.println("2. Return defensive copies");
        System.out.println("3. Never expose mutable arrays");
    }
}
