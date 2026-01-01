# LAB17: Minimize Mutability (Item 17)

## 🎯 Learning Objective

Master immutable class design, understanding the benefits (thread-safety, 
simplicity, caching) and techniques (defensive copies, final fields, 
no setters, static factories).

---

## 📖 Scenario

You're developing a **financial trading system** where price quotes, orders, 
and positions must be reliably shared across threads. The current mutable 
implementations have led to race conditions, inconsistent states, and subtle 
bugs that only appear under load.

---

## 📁 Files to Examine

**Flawed Implementation:**
```
flawed-implementations/src/main/java/effectivejava/flawed/chapter04/
├── lab17/
│   ├── Money.java              # Mutable money class
│   ├── DateRange.java          # Mutable with Date (extra problematic!)
│   ├── Portfolio.java          # Mutable collection exposed
│   └── ImmutabilityIssuesDemo.java
```

---

## 🔴 What's Wrong?

Study the implementations and identify:

1. **Mutable state** — Fields can be modified after construction
2. **Leaked references** — Mutable objects returned from getters
3. **No defensive copies** — Constructor stores mutable arguments directly
4. **Thread-safety issues** — Shared mutable state without synchronization
5. **Broken invariants** — State can become inconsistent

---

## 📋 Your Task

Create immutable versions in:
```
refactored-solutions/src/main/java/effectivejava/refactored/chapter04/lab17/
```

### Requirements:

1. **Make Money immutable:**
   - All fields final
   - No setters
   - Operations return new instances
   - Provide static factories (`of`, `ZERO`)

2. **Make DateRange immutable:**
   - Use `Instant` or `LocalDate` instead of `Date`
   - Or make defensive copies of Date
   - Validate start <= end

3. **Make Portfolio thread-safe:**
   - Return unmodifiable view of positions
   - Make defensive copies of input
   - Use `Collections.unmodifiableList()` or `List.copyOf()`

### Five rules of immutability:
1. Don't provide methods that modify state
2. Ensure class can't be extended (final class or private constructor)
3. Make all fields final
4. Make all fields private
5. Ensure exclusive access to mutable components

---

## ✅ Validation Steps

### Before Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter04Lab17BeforeTest"
```

Observe:
- Test `mutableMoneyLeadsToInconsistency` — Shows value changed unexpectedly
- Test `dateRangeInvariantBroken` — Start > end after external mutation
- Test `portfolioExternalModification` — Positions modified through getter

### After Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter04Lab17AfterTest"
```

Your code should:
- [ ] Pass `immutableMoney_operationsReturnNewInstance`
- [ ] Pass `immutableMoney_cannotModifyAfterCreation`
- [ ] Pass `dateRange_defensiveCopiesProtect`
- [ ] Pass `portfolio_returnedListIsUnmodifiable`
- [ ] Pass `threadSafety_concurrentAccessWorks`

---

## 🧪 Experiments

### Experiment 1: Thread Safety
1. Share a mutable Money between 100 threads
2. Have each thread call `add()` 
3. Check final value — is it correct?
4. Repeat with immutable version

### Experiment 2: Functional Operations
Chain operations on immutable Money:
```java
Money result = Money.ZERO
    .add(salary)
    .subtract(taxes)
    .add(bonus);
```
Notice how the pattern resembles Stream operations.

### Experiment 3: Instance Caching
Implement caching for common values (ZERO, ONE):
1. Use a static cache map
2. Ensure `Money.of(0)` always returns same instance

---

## 💭 Reflection Prompts

1. **What are the downsides of immutability?**
   - Object creation overhead
   - Multi-step builds
   - When is a Builder necessary?

2. **Final class vs private constructor:**
   - When would you choose each?
   - What about abstract immutable parents?

3. **Immutability and performance:**
   - When does object creation overhead matter?
   - How does escape analysis help?

4. **Records in Java 16+:**
   - How do records relate to immutability?
   - What do they provide automatically?

5. **Functional programming connection:**
   - How does immutability enable functional style?
   - What about "persistent" data structures?

---

## 🔗 Related Labs

- **LAB16** (Accessors vs Public Fields) — Encapsulation foundation
- **LAB50** (Defensive Copies) — Protecting mutable components
- **LAB78** (Synchronization) — Why immutability helps concurrency

---

## 📚 Reference

- Effective Java, Item 17: "Minimize mutability"
- Key insight: Classes should be immutable unless there's a good reason for them to be mutable
