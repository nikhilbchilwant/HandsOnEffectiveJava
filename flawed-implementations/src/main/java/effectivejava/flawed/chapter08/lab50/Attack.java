package effectivejava.flawed.chapter08.lab50;

import java.util.Date;

/**
 * DEMONSTRATION - Attacks on Period class
 * 
 * Shows how the lack of defensive copies allows breaking invariants.
 */
public class Attack {

    public static void main(String[] args) {
        // Attack 1: Modify mutable parameter after construction
        System.out.println("=== Attack 1: Parameter Mutation ===");
        
        Date start = new Date();
        Date end = new Date();
        Period p = new Period(start, end);
        System.out.println("Before attack: " + p);
        
        // The reference is shared - we can mutate it!
        end.setTime(start.getTime() - 86400000);  // Set to 1 day before start
        System.out.println("After attack:  " + p);
        System.out.println("Invariant broken? end=" + p.end() + " < start=" + p.start() + " : " + 
            (p.end().before(p.start())));
        
        System.out.println();
        
        // Attack 2: Modify return value from getter
        System.out.println("=== Attack 2: Return Value Mutation ===");
        
        Date start2 = new Date();
        Date end2 = new Date();
        Period p2 = new Period(start2, end2);
        System.out.println("Before attack: " + p2);
        
        // Get the reference and mutate it!
        p2.end().setTime(p2.start().getTime() - 86400000);
        System.out.println("After attack:  " + p2);
        System.out.println("Invariant broken? " + (p2.end().before(p2.start())));
        
        // In a security context, this could be:
        // - Bypassing access controls based on time
        // - Corrupting audit logs
        // - Violating business rules
    }
}
