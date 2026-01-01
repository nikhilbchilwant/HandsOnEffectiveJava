package effectivejava.labs.chapter03.lab11;

import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================================
 * LAB 11: Always Override hashCode When You Override equals (Item 11)
 * ============================================================================
 * Chapter 3, pp. 50-55
 * 
 * SCENARIO:
 * PhoneNumber overrides equals() but NOT hashCode(). This breaks
 * HashMap and HashSet — equal objects hash to different buckets!
 * 
 * THE BUG:
 * Objects that are equal MUST have the same hash code.
 * If they don't, hash-based collections won't find them.
 * 
 * YOUR TASK:
 * TODO: Implement hashCode() consistent with equals()
 * Rule: Equal objects must have equal hash codes.
 * ============================================================================
 */
public class PhoneNumber {

    private final short areaCode;
    private final short prefix;
    private final short lineNum;

    public PhoneNumber(int areaCode, int prefix, int lineNum) {
        this.areaCode = rangeCheck(areaCode, 999, "area code");
        this.prefix = rangeCheck(prefix, 999, "prefix");
        this.lineNum = rangeCheck(lineNum, 9999, "line num");
    }

    private static short rangeCheck(int val, int max, String arg) {
        if (val < 0 || val > max)
            throw new IllegalArgumentException(arg + ": " + val);
        return (short) val;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof PhoneNumber)) return false;
        PhoneNumber pn = (PhoneNumber) o;
        return pn.lineNum == lineNum 
            && pn.prefix == prefix
            && pn.areaCode == areaCode;
    }

    // =========================================================================
    // FIXME: Missing hashCode() breaks hash-based collections!
    // =========================================================================
    
    // TODO: Implement hashCode() using a formula like:
    // @Override
    // public int hashCode() {
    //     int result = Short.hashCode(areaCode);
    //     result = 31 * result + Short.hashCode(prefix);
    //     result = 31 * result + Short.hashCode(lineNum);
    //     return result;
    // }
    //
    // Or use Objects.hash() for convenience (slightly slower):
    // @Override
    // public int hashCode() {
    //     return Objects.hash(areaCode, prefix, lineNum);
    // }

    @Override
    public String toString() {
        return String.format("(%03d) %03d-%04d", areaCode, prefix, lineNum);
    }

    public static void main(String[] args) {
        System.out.println("=== hashCode Contract Demo ===\n");

        PhoneNumber pn1 = new PhoneNumber(707, 867, 5309);
        PhoneNumber pn2 = new PhoneNumber(707, 867, 5309);

        System.out.println("pn1: " + pn1);
        System.out.println("pn2: " + pn2);
        System.out.println("pn1.equals(pn2): " + pn1.equals(pn2));
        System.out.println("pn1.hashCode(): " + pn1.hashCode());
        System.out.println("pn2.hashCode(): " + pn2.hashCode());
        System.out.println("Same hashCode? " + (pn1.hashCode() == pn2.hashCode()));

        System.out.println("\n--- HashMap Test ---");
        Map<PhoneNumber, String> contacts = new HashMap<>();
        contacts.put(pn1, "Jenny");

        System.out.println("Looking up pn2 (equal to pn1)...");
        String found = contacts.get(pn2);
        System.out.println("Result: " + found);

        if (found == null) {
            System.out.println("\nBUG! Equal object not found in HashMap!");
            System.out.println("Cause: hashCode not overridden, so pn2 hashes");
            System.out.println("to a different bucket than pn1.");
            System.out.println("\nFIX: Implement hashCode() consistent with equals()");
        } else {
            System.out.println("\nSUCCESS! HashMap works correctly.");
        }
    }
}
