# LAB07: Eliminate Obsolete Object References (Item 7)

## 🎯 Learning Objective

Learn to identify and fix memory leaks caused by unintentional object retention,
understanding when the garbage collector cannot help you.

---

## 📖 Scenario

You're maintaining a **custom Stack implementation** used in a high-throughput 
transaction processing system. Operations report occasional OutOfMemoryErrors 
after extended runtime, but heap dumps show objects that should have been 
garbage collected long ago.

Additionally, you have a **Cache implementation** that never evicts entries, 
causing unbounded memory growth.

---

## 📁 Files to Examine

**Flawed Implementation:**
```
flawed-implementations/src/main/java/effectivejava/flawed/chapter02/
├── lab07/
│   ├── LeakyStack.java           # Stack that holds obsolete references
│   ├── LeakyCache.java           # Cache with memory leak
│   ├── LeakyListenerManager.java # Listener leak (common in GUIs/callbacks)
│   └── MemoryLeakDemo.java       # Demonstrates the leaks
```

---

## 🔴 What's Wrong?

Study the implementations and identify:

1. **Obsolete references** — Elements remain reachable after logical removal
2. **Unbounded caches** — No eviction policy, grows forever
3. **Listener leaks** — Registered callbacks never unregistered
4. **Unintentional object retention** — GC can't collect because references exist

---

## 📋 Your Task

Fix the memory leaks. Create your implementation in:
```
refactored-solutions/src/main/java/effectivejava/refactored/chapter02/lab07/
```

### Requirements:

1. **Fix LeakyStack:**
   - Null out references when popping
   - Shrink backing array when appropriate (optional)

2. **Fix LeakyCache:**
   - Use `WeakHashMap` when entries should be GC'd when keys unreachable
   - OR implement LRU eviction with `LinkedHashMap`
   - OR use time-based expiration

3. **Fix LeakyListenerManager:**
   - Use `WeakReference` for listeners
   - OR provide explicit `removeListener()` and document it

### Constraints:
- Stack must behave identically (same API, same ordering)
- Cache must still provide fast lookups
- Listeners must still receive callbacks while registered

---

## ✅ Validation Steps

### Before Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter02Lab07BeforeTest"
```

Run `MemoryLeakDemo.java` and observe:
- Memory usage grows even after elements are "removed"
- Heap dumps show retained objects

### After Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter02Lab07AfterTest"
```

Your code should:
- [ ] Pass `stack_noObsoleteReferences`
- [ ] Pass `cache_weakKeysGetCollected`
- [ ] Pass `listeners_canBeGarbageCollected`

---

## 🧪 Experiments

### Experiment 1: Visualize the Leak
1. Run the flawed Stack with 10,000 push/pop cycles
2. Take a heap dump using `jcmd <pid> GC.heap_dump heap.hprof`
3. Open in VisualVM or Eclipse MAT
4. Find the retained objects
5. Repeat with your fixed version

### Experiment 2: WeakReference Behavior
1. Create a WeakReference to an object
2. Hold a strong reference → verify object not collected
3. Clear strong reference → call `System.gc()` → check WeakReference

### Experiment 3: LinkedHashMap Access-Order Mode
Explore how `LinkedHashMap(capacity, loadFactor, accessOrder=true)` works
for LRU cache implementation.

---

## 💭 Reflection Prompts

1. **Why can't the GC handle this automatically?**
   - What does "reachable" mean to the GC?
   - Why is an array element still reachable after logical removal?

2. **When should you null out references?**
   - Always? Never? When?
   - What's the rule of thumb from the book?

3. **Weak references vs explicit removal:**
   - When is WeakHashMap appropriate?
   - When is it dangerous?

4. **Other sources of memory leaks:**
   - Static collections
   - Long-running threads
   - Native resources
   - What patterns help?

5. **Profiling and detection:**
   - How would you find this leak in production?
   - What tools and metrics help?

---

## 🔗 Related Labs

- **LAB08** (Finalizers/Cleaners) — Another resource management pattern
- **LAB09** (Try-With-Resources) — Deterministic resource cleanup

---

## 📚 Reference

- Effective Java, Item 7: "Eliminate obsolete object references"
- Key insight: Whenever a class manages its own memory, be alert for memory leaks
