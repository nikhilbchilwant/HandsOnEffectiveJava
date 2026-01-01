package effectivejava.labs.chapter06.lab37;

import java.util.*;

/**
 * ============================================================================
 * LAB 37: Use EnumMap Instead of Ordinal Indexing (Item 37)
 * ============================================================================
 * Chapter 6, pp. 171-176
 * 
 * SCENARIO:
 * Code uses enum.ordinal() as array index. This is error-prone!
 * EnumMap is type-safe and just as fast.
 * 
 * YOUR TASK:
 * TODO: Replace ordinal-indexed arrays with EnumMap
 * ============================================================================
 */
public class PlantCollection {

    public enum LifeCycle {
        ANNUAL, PERENNIAL, BIENNIAL
    }

    static class Plant {
        final String name;
        final LifeCycle lifeCycle;

        Plant(String name, LifeCycle lifeCycle) {
            this.name = name;
            this.lifeCycle = lifeCycle;
        }

        @Override
        public String toString() { return name; }
    }

    // =========================================================================
    // BAD: Using ordinal() as array index
    // =========================================================================
    
    public static void groupByOrdinal(List<Plant> plants) {
        // Array indexed by enum ordinal - FRAGILE!
        @SuppressWarnings("unchecked")
        Set<Plant>[] plantsByLifeCycle = (Set<Plant>[]) new Set[LifeCycle.values().length];

        for (int i = 0; i < plantsByLifeCycle.length; i++) {
            plantsByLifeCycle[i] = new HashSet<>();
        }

        for (Plant p : plants) {
            plantsByLifeCycle[p.lifeCycle.ordinal()].add(p);  // Using ordinal!
        }

        // Printing is awkward
        for (int i = 0; i < plantsByLifeCycle.length; i++) {
            System.out.printf("%s: %s%n", LifeCycle.values()[i], plantsByLifeCycle[i]);
        }
        
        // Problems:
        // - Array doesn't know what indexes mean
        // - Must manually match ordinal to enum
        // - If enum order changes, code breaks
    }

    // =========================================================================
    // GOOD: Using EnumMap
    // =========================================================================
    
    public static void groupByEnumMap(List<Plant> plants) {
        // EnumMap - type-safe, efficient, clear!
        Map<LifeCycle, Set<Plant>> plantsByLifeCycle = new EnumMap<>(LifeCycle.class);

        for (LifeCycle lc : LifeCycle.values()) {
            plantsByLifeCycle.put(lc, new HashSet<>());
        }

        for (Plant p : plants) {
            plantsByLifeCycle.get(p.lifeCycle).add(p);
        }

        System.out.println(plantsByLifeCycle);
        
        // Even better with streams:
        // Map<LifeCycle, Set<Plant>> result = plants.stream()
        //     .collect(Collectors.groupingBy(p -> p.lifeCycle,
        //         () -> new EnumMap<>(LifeCycle.class),
        //         Collectors.toSet()));
    }

    public static void main(String[] args) {
        List<Plant> garden = List.of(
            new Plant("Basil", LifeCycle.ANNUAL),
            new Plant("Carrot", LifeCycle.BIENNIAL),
            new Plant("Rosemary", LifeCycle.PERENNIAL),
            new Plant("Parsley", LifeCycle.BIENNIAL),
            new Plant("Thyme", LifeCycle.PERENNIAL)
        );

        System.out.println("=== BAD: ordinal() indexing ===");
        groupByOrdinal(garden);

        System.out.println("\n=== GOOD: EnumMap ===");
        groupByEnumMap(garden);

        System.out.println("\n--- Why EnumMap is better ---");
        System.out.println("1. Type-safe (can't use wrong enum or int)");
        System.out.println("2. As fast as array (backed by array internally)");
        System.out.println("3. Self-documenting (map keys are enums)");
    }
}
