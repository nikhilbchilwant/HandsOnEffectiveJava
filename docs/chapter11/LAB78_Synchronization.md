# LAB78: Synchronize Access to Shared Mutable Data (Item 78)

## 🎯 Learning Objective

Understand when and how to synchronize access to shared mutable data,
the difference between atomicity and visibility, and common concurrency pitfalls.

---

## 📖 Scenario

You're debugging a **background task scheduler** that uses shared state to 
track tasks, cancellation flags, and progress. The current implementation 
has race conditions, visibility issues, and relies on luck rather than 
correctness guarantees.

---

## 📁 Files to Examine

**Flawed Implementation:**
```
flawed-implementations/src/main/java/effectivejava/flawed/chapter11/
├── lab78/
│   ├── StopThread.java              # Classic visibility bug
│   ├── SerialNumberGenerator.java   # Atomicity bug (increment)
│   ├── TaskProgress.java            # Multiple fields, inconsistent state
│   └── ConcurrencyBugsDemo.java     # Demonstrates the failures
```

---

## 🔴 What's Wrong?

Study the implementations and identify:

1. **Visibility issues** — Thread changes not seen by other threads
2. **Atomicity issues** — `++` is not atomic, race conditions
3. **Inconsistent reads** — Seeing partial updates to multi-field state
4. **Missing volatile** — Compiler/CPU reordering breaks logic
5. **Incorrect synchronization** — Synchronized on wrong objects

---

## 📋 Your Task

Fix the concurrency bugs in:
```
refactored-solutions/src/main/java/effectivejava/refactored/chapter11/lab78/
```

### Requirements:

1. **Fix StopThread:**
   - Use `volatile` for the stop flag
   - OR use synchronized getters/setters
   - Thread must reliably stop when flag is set

2. **Fix SerialNumberGenerator:**
   - Use `synchronized` for the entire increment operation
   - OR use `AtomicLong`
   - No duplicate serial numbers, even under load

3. **Fix TaskProgress:**
   - Ensure consistent reads of related fields
   - Options: synchronized block, immutable snapshots, AtomicReference

### Understanding visibility vs atomicity:
- **Visibility**: Changes made by one thread are seen by others
- **Atomicity**: Operation completes without intermediate states being visible
- `volatile` provides visibility but NOT atomicity for compound operations

---

## ✅ Validation Steps

### Before Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter11Lab78BeforeTest"
```

Run multiple times — bugs may appear intermittently:
- Test `stopThread_mayNeverStop` — Thread runs forever
- Test `serialNumbers_hasDuplicates` — Same number returned twice
- Test `progress_inconsistentReads` — completedTasks > totalTasks!

### After Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter11Lab78AfterTest"
```

Your code should CONSISTENTLY:
- [ ] Pass `stopThread_stopsReliably`
- [ ] Pass `serialNumbers_noDuplicates`
- [ ] Pass `progress_alwaysConsistent`

---

## 🧪 Experiments

### Experiment 1: Reproduce the Stop Bug
Run StopThread without volatile/synchronized:
1. On some JVMs/CPUs, it never stops
2. With `-server` flag, more likely to break
3. Add `volatile` and observe the difference

### Experiment 2: Stress Test Serial Numbers
1. Create 10 threads, each generating 10,000 serial numbers
2. Collect all numbers in a ConcurrentHashMap
3. Check for duplicates
4. Compare: unsync'd `++` vs `synchronized` vs `AtomicLong`

### Experiment 3: Memory Ordering
Create a simple test that demonstrates reordering:
```java
int a = 0, b = 0;
Thread 1: a = 1; x = b;
Thread 2: b = 1; y = a;
// Can x == 0 && y == 0? YES (without synchronization)!
```

---

## 💭 Reflection Prompts

1. **Why doesn't volatile work for ++?**
   - What are the steps in `count++`?
   - What can interleave?

2. **Synchronized vs volatile vs atomics:**
   - When would you use each?
   - What are the performance trade-offs?

3. **False confidence from testing:**
   - Why do concurrency bugs often pass tests?
   - How would you test for race conditions?

4. **Happens-before relationship:**
   - What does the Java Memory Model guarantee?
   - What does "happens-before" mean?

5. **Immutability alternative:**
   - How does immutability eliminate synchronization needs?
   - Connection to LAB17?

---

## 🔗 Related Labs

- **LAB79** (Excessive Synchronization) — Don't overdo it
- **LAB80** (Executors vs Threads) — Modern concurrency abstractions
- **LAB83** (Lazy Initialization) — Getting it right with DCL

---

## 📚 Reference

- Effective Java, Item 78: "Synchronize access to shared mutable data"
- Key insight: Synchronization is required for both mutual exclusion AND visibility
