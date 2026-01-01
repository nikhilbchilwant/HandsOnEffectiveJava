package effectivejava.labs.chapter03.lab14;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import static java.util.Comparator.*;

/**
 * ============================================================================
 * LAB 14 (Part B): Modern Comparator Construction (Item 14)
 * ============================================================================
 * Chapter 3, pp. 69-70
 * 
 * SCENARIO:
 * You've implemented Comparable using Integer.compare() in Version.java.
 * Now learn the MODERN way: Comparator construction methods.
 * 
 * Old way (verbose, error-prone):
 * <pre>
 * public int compareTo(PhoneNumber pn) {
 *     int result = Short.compare(areaCode, pn.areaCode);
 *     if (result == 0) {
 *         result = Short.compare(prefix, pn.prefix);
 *         if (result == 0)
 *             result = Short.compare(lineNum, pn.lineNum);
 *     }
 *     return result;
 * }
 * </pre>
 * 
 * YOUR TASK:
 * TODO #1: Create a static Comparator<ComparablePhoneNumber> using 
 *          comparingInt() and thenComparingInt()
 * TODO #2: Implement compareTo() by delegating to the Comparator
 * TODO #3: Add proper equals() and hashCode()
 * 
 * HINT: Use static import: import static java.util.Comparator.*;
 * 
 * Pattern:
 *   private static final Comparator<T> COMPARATOR =
 *       comparingInt((T x) -> x.field1)
 *           .thenComparingInt(x -> x.field2)
 *           .thenComparingInt(x -> x.field3);
 * ============================================================================
 */
public final class ComparablePhoneNumber implements Comparable<ComparablePhoneNumber> {
    
    private final short areaCode, prefix, lineNum;

    public ComparablePhoneNumber(int areaCode, int prefix, int lineNum) {
        this.areaCode = rangeCheck(areaCode, 999, "area code");
        this.prefix   = rangeCheck(prefix,   999, "prefix");
        this.lineNum  = rangeCheck(lineNum, 9999, "line num");
    }

    private static short rangeCheck(int val, int max, String arg) {
        if (val < 0 || val > max)
            throw new IllegalArgumentException(arg + ": " + val);
        return (short) val;
    }

    // =========================================================================
    // TODO #3: Implement equals() properly
    // =========================================================================
    
    @Override 
    public boolean equals(Object o) {
        // FIXME: Implement equals checking all three fields
        // Remember: Check for same reference, instance of, then field comparison
        return false; // TODO
    }

    // =========================================================================
    // TODO #3: Implement hashCode() consistently with equals()
    // =========================================================================
    
    @Override 
    public int hashCode() {
        // FIXME: Implement using Objects.hash() or manual calculation
        // Hint: return Objects.hash(areaCode, prefix, lineNum);
        return 0; // TODO
    }

    @Override 
    public String toString() {
        return String.format("%03d-%03d-%04d", areaCode, prefix, lineNum);
    }

    // =========================================================================
    // TODO #1: Create static Comparator using construction methods
    // =========================================================================
    
    // private static final Comparator<ComparablePhoneNumber> COMPARATOR =
    //     comparingInt((ComparablePhoneNumber pn) -> pn.areaCode)
    //         .thenComparingInt(pn -> pn.prefix)
    //         .thenComparingInt(pn -> pn.lineNum);

    // =========================================================================
    // TODO #2: Implement compareTo() using the Comparator
    // =========================================================================
    
    @Override
    public int compareTo(ComparablePhoneNumber pn) {
        // FIXME: Replace manual comparison with Comparator
        
        // Old verbose way (replace this):
        int result = Short.compare(areaCode, pn.areaCode);
        if (result == 0) {
            result = Short.compare(prefix, pn.prefix);
            if (result == 0)
                result = Short.compare(lineNum, pn.lineNum);
        }
        return result;
        
        // Modern way (use this):
        // return COMPARATOR.compare(this, pn);
    }

    // =========================================================================
    // Demo
    // =========================================================================
    
    private static ComparablePhoneNumber randomPhoneNumber() {
        Random rnd = ThreadLocalRandom.current();
        return new ComparablePhoneNumber(
                rnd.nextInt(1000),
                rnd.nextInt(1000),
                rnd.nextInt(10000));
    }

    public static void main(String[] args) {
        System.out.println("=== Modern Comparator Construction Lab ===\n");

        // Generate random phone numbers
        NavigableSet<ComparablePhoneNumber> phoneNumbers = new TreeSet<>();
        for (int i = 0; i < 10; i++) {
            phoneNumbers.add(randomPhoneNumber());
        }

        System.out.println("Sorted phone numbers (TreeSet uses compareTo):");
        phoneNumbers.forEach(System.out::println);

        System.out.println("\n--- Current Implementation ---");
        System.out.println("Using manual nested if-statements (verbose)");
        
        System.out.println("\n--- Target Implementation ---");
        System.out.println("private static final Comparator<T> COMPARATOR =");
        System.out.println("    comparingInt((T x) -> x.field1)");
        System.out.println("        .thenComparingInt(x -> x.field2)");
        System.out.println("        .thenComparingInt(x -> x.field3);");
        System.out.println();
        System.out.println("public int compareTo(T other) {");
        System.out.println("    return COMPARATOR.compare(this, other);");
        System.out.println("}");
        
        System.out.println("\n--- Benefits ---");
        System.out.println("1. More readable - comparison chain is clear");
        System.out.println("2. Less error-prone - no manual result tracking");
        System.out.println("3. Easy to modify - add/reorder fields easily");
    }
}
