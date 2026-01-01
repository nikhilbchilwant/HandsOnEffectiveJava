# LAB01: Static Factory Methods vs Constructors (Item 1)

## 🎯 Learning Objective

Understand why static factory methods often outperform constructors, and learn to 
recognize when to refactor telescoping constructors into meaningful named factories.

---

## 📖 Scenario

You're building a **Connection Pool Manager** for a database client library. The current 
implementation uses multiple constructors with different parameter combinations (the 
"telescoping constructor" anti-pattern). Clients are confused about which constructor 
to use and what the default behaviors are.

---

## 📁 Files to Examine

**Flawed Implementation:**
```
flawed-implementations/src/main/java/effectivejava/flawed/chapter02/
├── lab01/
│   ├── DatabaseConnection.java      # Telescoping constructors
│   ├── ConnectionPool.java          # Uses the flawed connection class
│   └── ConnectionClient.java        # Demo client showing the confusion
```

---

## 🔴 What's Wrong?

Study the `DatabaseConnection` class and identify:

1. **Telescoping constructors** — Too many constructor overloads
2. **No meaningful names** — `new DatabaseConnection(true, false, 5000)` means what?
3. **No instance control** — Creates a new object every time, even for identical configs
4. **No return type flexibility** — Must return exact declared type
5. **Documentation burden** — Each constructor needs extensive Javadoc

---

## 📋 Your Task

Refactor `DatabaseConnection` using static factory methods. Create your implementation in:
```
refactored-solutions/src/main/java/effectivejava/refactored/chapter02/lab01/
```

### Requirements:

1. **Create named factory methods** such as:
   - `localDevConnection()`
   - `productionConnection(String host, int port)`
   - `pooledConnection(int poolSize, boolean lazyInit)`
   - `fromProperties(Properties config)`

2. **Implement instance caching** where appropriate (e.g., dev connections)

3. **Return interface type** where useful (if you create a `Connection` interface)

4. **Make constructor(s) private**

### Constraints:
- Do NOT change the public API behavior
- Existing client code should work with minimal changes
- Add appropriate documentation

---

## ✅ Validation Steps

### Before Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter02Lab01BeforeTest"
```

Expected results:
- Test `constructorConfusion_multipleCombinations` — PASSES (but demonstrates the problem)
- Test `noInstanceCaching_createsNewObjectsAlways` — PASSES (shows wasteful allocation)
- Test `parameterMeaning_unclearFromSignature` — See the confusing API

### After Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter02Lab01AfterTest"
```

Your refactored code should:
- [ ] Pass all behavior tests (same functionality)
- [ ] Pass `factoryMethodNaming_isSelfDocumenting` 
- [ ] Pass `instanceCaching_reusesIdenticalConnections`
- [ ] Pass `returnTypeFlexibility_canReturnSubtypes`

---

## 🧪 Experiments

### Experiment 1: Instance Caching Impact
1. Create 1000 "local dev" connections using the flawed version
2. Create 1000 "local dev" connections using your cached factory
3. Use `-XX:+PrintGCDetails` to observe allocation differences

### Experiment 2: API Clarity
1. Ask a colleague to use `new DatabaseConnection(...)` — time how long it takes to find the right constructor
2. Ask them to use your factory methods — compare discovery time

---

## 💭 Reflection Prompts

After completing the lab, consider:

1. **When would you still prefer a constructor?**
   - Think about simple value classes, performance-critical paths

2. **How does this pattern affect testability?**
   - Mock injection, subclass substitution

3. **What are the downsides of static factories?**
   - Discoverability (not as obvious as constructors)
   - Subclassing complications
   - Can make the API feel "non-standard"

4. **How would you name these factories in your own codebase?**
   - `of`, `from`, `valueOf`, `getInstance`, `create`, `newInstance`?
   - What's the naming convention in your team?

5. **Service Provider Framework connection:**
   - How do static factories relate to `DriverManager.getConnection()`?

---

## 🔗 Related Labs

- **LAB02** (Builders) — Another creational pattern for complex objects
- **LAB03** (Singletons) — Static factories for instance-controlled classes
- **LAB05** (Dependency Injection) — When factories aren't enough

---

## 📚 Reference

- Effective Java, Item 1: "Consider static factory methods instead of constructors"
- Key advantages: meaningful names, instance control, return type flexibility, reduced verbosity
