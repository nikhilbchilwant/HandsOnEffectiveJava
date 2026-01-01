package effectivejava.labs.chapter07.lab44;

import java.util.function.*;

/**
 * ============================================================================
 * LAB 44: Favor Standard Functional Interfaces (Item 44)
 * ============================================================================
 * Chapter 7, pp. 199-203
 * 
 * SCENARIO:
 * Code creates custom functional interfaces when standard ones exist.
 * java.util.function has 43 interfaces covering most cases.
 * 
 * YOUR TASK:
 * TODO: Replace custom interfaces with standard ones
 * ============================================================================
 */
public class StandardFunctionalInterfaces {

    // =========================================================================
    // BAD: Custom functional interface when standard exists
    // =========================================================================

    // Don't define this!
    @FunctionalInterface
    interface StringProcessor {
        String process(String input);
    }

    // Use UnaryOperator<String> instead!

    // =========================================================================
    // The 6 basic standard functional interfaces
    // =========================================================================

    public static void demonstrateBasicInterfaces() {
        // 1. UnaryOperator<T>: T apply(T t) - same type in and out
        UnaryOperator<String> toUpper = String::toUpperCase;
        System.out.println("UnaryOperator: " + toUpper.apply("hello"));

        // 2. BinaryOperator<T>: T apply(T t1, T t2) - two of same type
        BinaryOperator<Integer> add = Integer::sum;
        System.out.println("BinaryOperator: " + add.apply(3, 5));

        // 3. Predicate<T>: boolean test(T t) - test a condition
        Predicate<String> isEmpty = String::isEmpty;
        System.out.println("Predicate: " + isEmpty.test(""));

        // 4. Function<T, R>: R apply(T t) - transform type
        Function<String, Integer> length = String::length;
        System.out.println("Function: " + length.apply("hello"));

        // 5. Supplier<T>: T get() - produce a value
        Supplier<Double> random = Math::random;
        System.out.println("Supplier: " + random.get());

        // 6. Consumer<T>: void accept(T t) - consume a value
        Consumer<String> print = System.out::println;
        print.accept("Consumer: hello");
    }

    // =========================================================================
    // When to write your own
    // =========================================================================

    // Custom interface IS appropriate when:
    // 1. Common use case with good descriptive name (e.g., Comparator)
    // 2. Has a strong contract with documentation
    // 3. Benefits from custom default methods

    @FunctionalInterface
    public interface EqualityChecker<T> {
        boolean areEqual(T first, T second);
        
        // Good reason: descriptive default method
        default boolean areNotEqual(T first, T second) {
            return !areEqual(first, second);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Standard Functional Interfaces ===\n");

        demonstrateBasicInterfaces();

        System.out.println("\n--- 6 Basic Interfaces ---");
        System.out.println("UnaryOperator<T>   : T → T      (String::toUpperCase)");
        System.out.println("BinaryOperator<T>  : (T,T) → T  (Integer::sum)");
        System.out.println("Predicate<T>       : T → boolean (String::isEmpty)");
        System.out.println("Function<T,R>      : T → R      (String::length)");
        System.out.println("Supplier<T>        : () → T     (Math::random)");
        System.out.println("Consumer<T>        : T → void   (System.out::println)");

        System.out.println("\n--- Variants ---");
        System.out.println("Bi-: two args (BiFunction, BiPredicate, BiConsumer)");
        System.out.println("Int/Long/Double: primitives (IntPredicate, LongSupplier)");
        System.out.println("ToInt/ToLong/ToDouble: return primitives");
    }
}
