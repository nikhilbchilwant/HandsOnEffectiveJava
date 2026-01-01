package effectivejava.labs.chapter07.lab48;

import java.math.BigInteger;
import java.util.stream.*;

/**
 * ============================================================================
 * LAB 48: Use Caution When Making Streams Parallel (Item 48)
 * ============================================================================
 * Chapter 7, pp. 222-226
 * 
 * SCENARIO:
 * Parallel streams are added without understanding when they help/hurt.
 * Wrong parallelization can be SLOWER, cause bugs, or even hang!
 * 
 * YOUR TASK:
 * TODO: Identify when parallel streams are appropriate
 * ============================================================================
 */
public class ParallelStreamCaution {

    // =========================================================================
    // BAD: Parallel on LinkedList (poor splittability)
    // =========================================================================

    public long sumLinkedListParallel() {
        var list = new java.util.LinkedList<Long>();
        for (long i = 0; i < 10_000; i++) list.add(i);
        
        // BAD: LinkedList has terrible splittability
        return list.parallelStream().mapToLong(Long::longValue).sum();
        // Single thread would be faster!
    }

    // =========================================================================
    // BAD: Parallel on stateful/ordered operations
    // =========================================================================

    public void limitWithParallel() {
        // This can be MUCH slower with parallel!
        Stream.iterate(1, n -> n + 1)
              .parallel()  // BAD with iterate!
              .limit(10)
              .forEach(System.out::println);
        // iterate() is inherently sequential
        // parallel + limit = performance disaster
    }

    // =========================================================================
    // GOOD: Parallel on arrays/ArrayLists with simple operations
    // =========================================================================

    public long sumArrayParallel(long[] array) {
        // GOOD: Arrays split perfectly, sum is easy to parallelize
        return LongStream.of(array).parallel().sum();
    }

    // =========================================================================
    // GOOD: CPU-intensive independent computations
    // =========================================================================

    public long countPrimesParallel(long n) {
        // Prime checking is CPU-intensive, perfectly parallel
        return LongStream.rangeClosed(2, n)
                .parallel()
                .filter(ParallelStreamCaution::isPrime)
                .count();
    }

    static boolean isPrime(long n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (long i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("=== Parallel Stream Caution ===\n");

        ParallelStreamCaution demo = new ParallelStreamCaution();

        // Good case: array with CPU-intensive work
        System.out.println("Counting primes up to 100,000...");
        long start = System.nanoTime();
        long count = demo.countPrimesParallel(100_000);
        long time = System.nanoTime() - start;
        System.out.printf("Found %d primes in %.2f ms%n", count, time / 1e6);

        System.out.println("\n--- When to use parallel ---");
        System.out.println("SOURCE: ArrayList, array, IntStream.range");
        System.out.println("OPERATION: reduce, count, sum, min, max");
        System.out.println("WORK: CPU-intensive per element");
        System.out.println("ELEMENTS: Many (>10,000)");

        System.out.println("\n--- When to AVOID parallel ---");
        System.out.println("SOURCE: LinkedList, iterate(), Stream.concat");
        System.out.println("OPERATION: limit, findFirst, forEachOrdered");
        System.out.println("SIDE EFFECTS: Writes to shared state");
    }
}
