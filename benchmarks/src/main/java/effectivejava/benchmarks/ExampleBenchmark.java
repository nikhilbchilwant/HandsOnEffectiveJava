package effectivejava.benchmarks;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * JMH Benchmark template for performance experiments.
 * 
 * To run:
 *   cd benchmarks
 *   mvn clean package
 *   java -jar target/benchmarks.jar
 * 
 * Or run specific benchmark:
 *   java -jar target/benchmarks.jar StringConcatenation
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
public class ExampleBenchmark {

    private static final int ITERATIONS = 1000;
    
    // ========================================
    // LAB63: String Concatenation Performance
    // ========================================
    
    @Benchmark
    public String stringConcatenation_plusOperator() {
        String result = "";
        for (int i = 0; i < ITERATIONS; i++) {
            result = result + "item" + i;  // Creates new String each time!
        }
        return result;
    }
    
    @Benchmark
    public String stringConcatenation_stringBuilder() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ITERATIONS; i++) {
            sb.append("item").append(i);
        }
        return sb.toString();
    }
    
    // ========================================
    // LAB06: Avoiding Unnecessary Objects
    // ========================================
    
    @Benchmark
    public long sumBoxed() {
        Long sum = 0L;  // Boxed - creates ~2 billion Long objects!
        for (long i = 0; i < Integer.MAX_VALUE / 1000; i++) {
            sum += i;  // Autoboxing creates new Long each time
        }
        return sum;
    }
    
    @Benchmark
    public long sumPrimitive() {
        long sum = 0L;  // Primitive - no object creation
        for (long i = 0; i < Integer.MAX_VALUE / 1000; i++) {
            sum += i;
        }
        return sum;
    }
    
    // ========================================
    // Entry point for running benchmarks
    // ========================================
    
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(ExampleBenchmark.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}
