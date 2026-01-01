package effectivejava.flawed.chapter08.lab50;

import java.util.Date;

/**
 * FLAWED IMPLEMENTATION - Period without defensive copies
 * 
 * This class appears to enforce that start <= end, but the invariant
 * can be broken by mutating the Date objects after construction.
 */
public class Period {

    private final Date start;
    private final Date end;

    /**
     * FLAWED: Stores direct references to mutable arguments.
     * 
     * Attack:
     *   Date start = new Date();
     *   Date end = new Date();
     *   Period p = new Period(start, end);
     *   end.setYear(70);  // Mutate to 1970 - now end < start!
     */
    public Period(Date start, Date end) {
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException("start after end");
        }
        // PROBLEM: Storing references directly!
        this.start = start;
        this.end = end;
    }

    /**
     * FLAWED: Returns mutable internal reference.
     * 
     * Attack:
     *   Period p = new Period(start, end);
     *   p.end().setYear(70);  // Mutate through getter!
     */
    public Date start() {
        return start;  // PROBLEM: Returns mutable reference!
    }

    public Date end() {
        return end;  // PROBLEM: Same issue!
    }

    @Override
    public String toString() {
        return "Period[" + start + " to " + end + "]";
    }

    // The class LOOKS immutable:
    // - Fields are final
    // - No setters
    // - Validation in constructor
    //
    // But it's NOT immutable because Date is mutable
    // and we store/return direct references.
}
