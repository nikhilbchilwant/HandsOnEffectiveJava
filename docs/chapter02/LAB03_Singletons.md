# LAB03: Enforce Singleton with Private Constructor or Enum (Item 3)

## 🎯 Learning Objective

Understand various singleton implementation approaches, their vulnerabilities, 
and why enum-based singletons are often preferred.

---

## 📖 Scenario

You're building a **Configuration Manager** that loads application settings from 
multiple sources (files, environment variables, remote config service). Only one 
instance should exist - multiple instances would cause inconsistent configuration 
states and potential security issues.

The current implementation uses a naive approach vulnerable to reflection attacks, 
serialization attacks, and multi-threading issues.

---

## 📁 Files to Examine

**Flawed Implementation:**
```
flawed-implementations/src/main/java/effectivejava/flawed/chapter02/
├── lab03/
│   ├── ConfigurationManager.java     # Naive singleton (public field)
│   ├── LazyConfigManager.java        # Broken lazy initialization
│   ├── SingletonBreaker.java         # Demonstrates vulnerabilities
│   └── ConfigManagerClient.java      # Usage examples
```

---

## 🔴 What's Wrong?

Study the singleton implementations and identify:

1. **Reflection attack vulnerability** — Can create second instance via reflection
2. **Serialization attack** — Deserializing creates a new instance
3. **Thread-safety issues** — Lazy initialization race condition
4. **Missing defensive measures** — No protection against attacks

---

## 📋 Your Task

Create THREE singleton implementations and compare them:

```
refactored-solutions/src/main/java/effectivejava/refactored/chapter02/lab03/
```

### Implementation 1: Static Factory Singleton
- Private static final field
- Static factory `getInstance()`
- Prevent reflection attacks (throw if already instantiated)
- Implement `readResolve()` for serialization safety

### Implementation 2: Double-Checked Locking (DCL)
- Lazy initialization with proper volatiles
- Thread-safe without full synchronization overhead
- Include the subtle bug fixes needed for DCL to work

### Implementation 3: Enum Singleton (Recommended)
- Single-element enum
- Automatic serialization safety
- Automatic reflection safety

### Requirements for all:
- Must pass singleton guarantee tests
- Must resist reflection attacks
- Must work correctly under serialization

---

## ✅ Validation Steps

### Before Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter02Lab03BeforeTest"
```

Expected failures:
- `reflectionAttack_createsSecondInstance` — PASSES (showing the vulnerability!)
- `serializationAttack_createsSecondInstance` — PASSES (showing the vulnerability!)
- `lazyInit_raceCondition` — May create multiple instances

### After Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter02Lab03AfterTest"
```

Your implementations should:
- [ ] Pass `enumSingleton_resistsReflection`
- [ ] Pass `enumSingleton_survivesSerializationRoundTrip`
- [ ] Pass `staticFactory_resistsReflection`
- [ ] Pass `dcl_threadSafeUnderLoad`
- [ ] Demonstrate identical instance across all access patterns

---

## 🧪 Experiments

### Experiment 1: Break the Naive Singleton
Run `SingletonBreaker.java` and observe how easily the singleton guarantee is violated.

### Experiment 2: Serialization Round-Trip
1. Serialize the singleton to a file
2. Deserialize it twice
3. Compare object identity (`==`) with original

### Experiment 3: Concurrent Access
1. Spawn 100 threads all calling `getInstance()` simultaneously
2. Collect all returned instances
3. Verify they're all the same object

### Experiment 4: Reflection Performance
Compare performance of enum singleton vs static factory under heavy access.

---

## 💭 Reflection Prompts

1. **Why is enum considered the best approach?**
   - Free serialization handling, reflection protection, concise

2. **When might enum NOT be suitable?**
   - Need to extend a class? Lazy initialization benefits?

3. **Singletons and testability:**
   - How do singletons affect unit testing?
   - What patterns help (dependency injection, testing hooks)?

4. **Double-checked locking history:**
   - Why was DCL broken before Java 5?
   - What role does `volatile` play?

5. **Singleton vs. static utilities:**
   - When would you choose a singleton over a utility class with static methods?

---

## 🔗 Related Labs

- **LAB04** (Noninstantiable Classes) — When you DON'T want any instances
- **LAB05** (Dependency Injection) — An alternative to singletons
- **LAB78** (Synchronization) — Thread-safety fundamentals

---

## 📚 Reference

- Effective Java, Item 3: "Enforce the singleton property with a private constructor or an enum type"
- Key insight: The single-element enum is the best way to implement a singleton
