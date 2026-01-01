package effectivejava.flawed.chapter07.lab45;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * FLAWED IMPLEMENTATION - Stream overuse example
 * 
 * This implementation uses streams where loops would be clearer.
 * Nested flatMap for Cartesian product is confusing.
 */
public class CartesianProduct {

    /**
     * Bad: Cartesian product with nested flatMap is hard to follow.
     * 
     * What this does: Generate all pairs (x, y) where x in xs and y in ys.
     */
    public static <T, U> List<Pair<T, U>> cartesianProductStream(
            Collection<T> xs, Collection<U> ys) {
        return xs.stream()
                .flatMap(x -> ys.stream()
                        .map(y -> new Pair<>(x, y)))
                .toList();
    }

    /**
     * EVEN WORSE: Triple Cartesian product - nearly unreadable!
     */
    public static <T, U, V> List<Triple<T, U, V>> tripleCartesianProductStream(
            Collection<T> xs, Collection<U> ys, Collection<V> zs) {
        return xs.stream()
                .flatMap(x -> ys.stream()
                        .flatMap(y -> zs.stream()
                                .map(z -> new Triple<>(x, y, z))))
                .toList();
    }

    /**
     * Better: Loop version is much clearer for Cartesian product.
     */
    public static <T, U> List<Pair<T, U>> cartesianProductLoop(
            Collection<T> xs, Collection<U> ys) {
        List<Pair<T, U>> result = new ArrayList<>();
        for (T x : xs) {
            for (U y : ys) {
                result.add(new Pair<>(x, y));
            }
        }
        return result;
    }

    /**
     * Simple pair class.
     */
    public record Pair<T, U>(T first, U second) {}

    /**
     * Simple triple class.
     */
    public record Triple<T, U, V>(T first, U second, V third) {}

    public static void main(String[] args) {
        List<String> colors = List.of("Red", "Blue");
        List<Integer> sizes = List.of(1, 2, 3);

        System.out.println("Stream version:");
        cartesianProductStream(colors, sizes).forEach(System.out::println);

        System.out.println("\nLoop version:");
        cartesianProductLoop(colors, sizes).forEach(System.out::println);

        // Both produce same result, but which is easier to understand?
    }
}
