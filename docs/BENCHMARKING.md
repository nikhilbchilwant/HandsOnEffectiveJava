# Benchmarking Guide

This document explains how to run performance experiments and benchmarks 
for the Effective Java labs that focus on performance trade-offs.

---

## 🔧 Setup

### Prerequisites
- Java 17+
- Maven 3.8+

### Build the Benchmarks Module

```bash
cd benchmarks
mvn clean package
```

This creates an executable JAR at `target/benchmarks.jar`.

---

## 🏃 Running Benchmarks

### Run All Benchmarks

```bash
java -jar target/benchmarks.jar
```

### Run Specific Benchmark

```bash
java -jar target/benchmarks.jar StringConcatenation
```

### Run with Custom Parameters

```bash
java -jar target/benchmarks.jar -wi 5 -i 10 -f 3 -t 1
```

Options:
- `-wi <iterations>`: Warmup iterations (default: 5)
- `-i <iterations>`: Measurement iterations (default: 10)
- `-f <forks>`: Number of forks (default: 1)
- `-t <threads>`: Number of threads (default: 1)

---

## 📊 Benchmark Categories

### LAB06: Unnecessary Objects

Compare object creation patterns:
- Autoboxing vs primitives
- Pattern.compile() caching vs repeated compilation
- String.matches() vs precompiled Pattern

### LAB63: String Concatenation

Compare string building approaches:
- `+` operator in loops
- StringBuilder
- StringBuffer (synchronized)
- String.join()

### LAB48: Parallel Streams

Compare sequential vs parallel performance:
- CPU-bound operations
- I/O-bound operations
- Small vs large data sets

---

## 📈 Interpreting Results

```
Benchmark                            Mode  Cnt    Score    Error  Units
StringConcat.plusOperator            avgt    5  1234.567 ± 12.34  ns/op
StringConcat.stringBuilder           avgt    5    45.678 ±  1.23  ns/op
```

- **Mode**: `avgt` = average time per operation
- **Cnt**: Number of measurement iterations
- **Score**: Average time (lower is better for avgt)
- **Error**: ± margin at 99.9% confidence
- **Units**: Time unit (ns = nanoseconds)

---

## ⚠️ Benchmarking Best Practices

1. **Run JIT-warmed**: Always include warmup iterations

2. **Multiple forks**: Use `-f 3` or more for reliable results

3. **Avoid dead code elimination**: Return computed values

4. **Watch for constant folding**: Use @State and Blackhole

5. **Profile first**: Use `-prof gc` to see allocation rates

---

## 🧪 Ad-Hoc Performance Experiments

For quick experiments without JMH:

```java
long start = System.nanoTime();
// operation to measure
long elapsed = System.nanoTime() - start;
System.out.printf("Elapsed: %.3f ms%n", elapsed / 1_000_000.0);
```

⚠️ Warning: Microbenchmarks without JMH are often misleading!

---

## 📚 Resources

- [JMH Documentation](https://openjdk.java.net/projects/code-tools/jmh/)
- [JMH Samples](https://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/)
