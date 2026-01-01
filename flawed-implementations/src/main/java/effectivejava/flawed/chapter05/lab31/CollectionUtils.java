package effectivejava.flawed.chapter05.lab31;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * FLAWED IMPLEMENTATION - Utility methods without bounded wildcards
 * 
 * These utility methods are less flexible than they could be because
 * they don't use bounded wildcards.
 */
public class CollectionUtils {

    private CollectionUtils() {}

    /**
     * FLAWED: Won't work with List<Integer> when comparing as Number.
     * 
     * List<Integer> integers = List.of(1, 2, 3);
     * Integer max = max(integers, Comparator.naturalOrder());  
     * // Works
     * 
     * But what if Integer only implements Comparable<Number>?
     * Or if we have a more complex hierarchy?
     */
    public static <T extends Comparable<T>> T max(Collection<T> coll) {
        if (coll.isEmpty()) {
            throw new IllegalArgumentException("Collection is empty");
        }
        T result = null;
        for (T t : coll) {
            if (result == null || t.compareTo(result) > 0) {
                result = t;
            }
        }
        return result;
    }

    /**
     * FLAWED: Too restrictive - same type for source and destination.
     * 
     * Can't do:
     *   copy(integerList, numberList)  // copy Integers into Number list
     */
    public static <T> void copy(List<T> src, List<T> dst) {
        for (T t : src) {
            dst.add(t);
        }
    }

    /**
     * FLAWED: Only accepts exact type match Comparator.
     * 
     * Can't use a Comparator<Number> to find max in List<Integer>.
     */
    public static <T> T maxWithComparator(Collection<T> coll, Comparator<T> comp) {
        if (coll.isEmpty()) {
            throw new IllegalArgumentException("Collection is empty");
        }
        T result = null;
        for (T t : coll) {
            if (result == null || comp.compare(t, result) > 0) {
                result = t;
            }
        }
        return result;
    }

    /**
     * FLAWED: Can't merge lists of subtypes.
     * 
     * List<Number> result = merge(integers, doubles);  // ERROR!
     */
    public static <T> List<T> merge(List<T> first, List<T> second) {
        List<T> result = new java.util.ArrayList<>(first);
        result.addAll(second);
        return result;
    }

    // The correct signatures would use ? extends and ? super
    // to maximize flexibility while maintaining type safety.
}
