package effectivejava.labs.chapter09.lab67;

/**
 * ============================================================================
 * LAB 67: Optimize Judiciously (Item 67)
 * ============================================================================
 * Chapter 9, pp. 286-290
 * 
 * SCENARIO:
 * Premature optimization - the root of all evil! Good design first.
 * 
 * YOUR TASK:
 * TODO: Focus on good design; optimize only when necessary with measurement
 * ============================================================================
 */
public class OptimizeJudiciously {

    // =========================================================================
    // Classic quotes about optimization
    // =========================================================================

    // "More computing sins are committed in the name of efficiency 
    //  (without necessarily achieving it) than for any other single reason
    //  - including blind stupidity." - W.A. Wulf

    // "We should forget about small efficiencies, say about 97% of the time: 
    //  premature optimization is the root of all evil." - Donald Knuth

    // "We follow two rules in the matter of optimization:
    //  Rule 1. Don't do it.
    //  Rule 2 (for experts only). Don't do it yet." - M.A. Jackson

    // =========================================================================
    // BAD: Micro-optimization that hurts readability
    // =========================================================================

    // "Optimized" but unreadable
    int multiplyBy15Bad(int n) {
        return (n << 4) - n;  // n * 16 - n = n * 15
        // JIT compiler does this anyway!
    }

    // Clear and JIT will optimize
    int multiplyBy15Good(int n) {
        return n * 15;
    }

    // =========================================================================
    // Design decisions that ARE worth considering for performance
    // =========================================================================

    // 1. API design: Don't return mutable internals (forces defensive copies)
    // 2. Data structures: Choose appropriate collections up front
    // 3. Return types: Don't require excessive processing

    // BAD API: Forces callers to copy
    // public List<String> getItems() { return items; }

    // GOOD API: Returns unmodifiable view
    // public List<String> getItems() { return Collections.unmodifiableList(items); }

    // =========================================================================
    // When you DO optimize
    // =========================================================================

    // 1. Profile first! Find the actual bottleneck
    // 2. Measure before and after
    // 3. Test on realistic data
    // 4. Check on different JVMs and platforms

    public static void main(String[] args) {
        System.out.println("=== Optimize Judiciously ===\n");

        System.out.println("THREE RULES:");
        System.out.println("1. Don't optimize");
        System.out.println("2. Don't optimize yet (for experts)");
        System.out.println("3. Measure before and after");

        System.out.println("\n--- Good Design > Optimization ---");
        System.out.println("Good design often yields good performance");
        System.out.println("Bad design is hard to optimize later");

        System.out.println("\n--- When to think about performance ---");
        System.out.println("✓ API design (hard to change later)");
        System.out.println("✓ Wire formats (protocols, persistence)");
        System.out.println("✓ Data structure choices");
        System.out.println("✗ Micro-optimizations (JIT handles these)");

        System.out.println("\n--- Optimization Process ---");
        System.out.println("1. Write clear, correct code first");
        System.out.println("2. Measure ACTUAL performance");
        System.out.println("3. If too slow, PROFILE to find bottleneck");
        System.out.println("4. Optimize the bottleneck");
        System.out.println("5. Measure again to verify improvement");
    }
}
