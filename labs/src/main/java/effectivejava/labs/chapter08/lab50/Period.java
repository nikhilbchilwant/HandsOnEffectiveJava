package effectivejava.labs.chapter08.lab50;

import java.util.Date;

/**
 * ============================================================================
 * LAB 50: Make Defensive Copies When Needed (Item 50)
 * ============================================================================
 * Chapter 8, pp. 231-236
 * 
 * SCENARIO:
 * A Period class stores start/end dates. It looks immutable but isn't!
 * Callers can modify the Date objects after construction.
 * 
 * YOUR TASK:
 * TODO #1: Make defensive copies in constructor
 * TODO #2: Make defensive copies in getters (or return immutable views)
 * ============================================================================
 */
public class Period {

    private final Date start;  // final doesn't help - Date is mutable!
    private final Date end;

    // =========================================================================
    // FIXME: No defensive copy - caller can modify after construction!
    // =========================================================================
    
    public Period(Date start, Date end) {
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException("Start after end");
        }
        // PROBLEM: Storing references to mutable objects!
        this.start = start;
        this.end = end;
        
        // TODO: Make defensive copies:
        // this.start = new Date(start.getTime());
        // this.end = new Date(end.getTime());
        // // Check validity AFTER copying (TOCTOU attack prevention)
        // if (this.start.compareTo(this.end) > 0) {
        //     throw new IllegalArgumentException("Start after end");
        // }
    }

    // =========================================================================
    // FIXME: Returning mutable internal objects!
    // =========================================================================
    
    public Date getStart() {
        return start;  // PROBLEM: Caller can modify!
        // TODO: return new Date(start.getTime());
    }

    public Date getEnd() {
        return end;  // PROBLEM: Caller can modify!
        // TODO: return new Date(end.getTime());
    }

    @Override
    public String toString() {
        return "Period[" + start + " - " + end + "]";
    }

    public static void main(String[] args) {
        System.out.println("=== Defensive Copies ===\n");

        Date start = new Date();
        Date end = new Date(start.getTime() + 86400000);  // +1 day

        Period period = new Period(start, end);
        System.out.println("Created: " + period);

        // ATTACK 1: Modify via constructor parameter
        System.out.println("\n--- Attack via constructor parameter ---");
        end.setTime(start.getTime() - 86400000);  // Set end BEFORE start!
        System.out.println("After modifying 'end': " + period);
        System.out.println("Invariant broken! End is before start!");

        // Reset for next attack
        Period period2 = new Period(new Date(), new Date(System.currentTimeMillis() + 86400000));
        System.out.println("\n--- Attack via getter ---");
        System.out.println("Before: " + period2);
        period2.getEnd().setTime(0);  // Modify via getter!
        System.out.println("After: " + period2);

        System.out.println("\n--- Solution ---");
        System.out.println("1. Copy in constructor: this.start = new Date(start.getTime())");
        System.out.println("2. Copy in getters: return new Date(start.getTime())");
        System.out.println("3. Better: Use java.time.Instant (immutable!)");
    }
}
