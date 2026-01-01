# LAB18: Favor Composition Over Inheritance (Item 18)

## 🎯 Learning Objective

Understand why inheritance breaks encapsulation and how the Decorator pattern 
(composition + forwarding) provides a safer alternative.

---

## 📖 Scenario

Your team has created an **InstrumentedHashSet** that counts additions by extending 
HashSet. It has a subtle bug: the count is wrong! Additionally, there's a 
**LoggingList** extending ArrayList with similar issues.

---

## 📁 Files to Examine

**Flawed Implementation:**
```
flawed-implementations/src/main/java/effectivejava/flawed/chapter04/
├── lab18/
│   ├── InstrumentedHashSet.java   # Broken counting via inheritance
│   ├── LoggingList.java           # Another broken inheritance example
│   └── InheritanceProblemsDemo.java
```

---

## 🔴 What's Wrong?

Study `InstrumentedHashSet` and identify:

1. **Self-use**: Parent's `addAll()` calls `add()`, causing double-counting
2. **Encapsulation leak**: Subclass depends on implementation details
3. **Fragility**: Parent class changes can silently break subclass
4. **Contract violations**: May not satisfy parent's contract

```java
InstrumentedHashSet<String> s = new InstrumentedHashSet<>();
s.addAll(List.of("a", "b", "c"));
s.getAddCount();  // Returns 6, not 3!
```

---

## 📋 Your Task

Refactor using composition (the Decorator pattern) in:
```
refactored-solutions/src/main/java/effectivejava/refactored/chapter04/lab18/
```

### Requirements:

1. **Create a ForwardingSet class:**
   - Implements `Set<E>`
   - Wraps another Set via composition
   - Forwards all methods to the wrapped set

2. **Create InstrumentedSet extending ForwardingSet:**
   - Overrides only `add()` and `addAll()`
   - Count increments correctly regardless of self-use

3. **Works with ANY Set implementation:**
   - Unlike inheritance, can wrap TreeSet, LinkedHashSet, etc.

### Pattern:

```
Interface        Implementation     Forwarding         Decorator
   Set      -->    HashSet      <--  ForwardingSet <-- InstrumentedSet
                                        wraps
```

---

## ✅ Validation Steps

### Before Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter04Lab18BeforeTest"
```

- Test `addAll_countsIncorrectly` — Count is doubled!

### After Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter04Lab18AfterTest"
```

- [ ] Pass `addAll_countsCorrectly`
- [ ] Pass `worksWithDifferentSetImplementations`
- [ ] Pass `forwardingPreservesBehavior`

---

## 🧪 Experiments

### Experiment 1: Self-Use Discovery
Without reading docs, how would you discover that `HashSet.addAll()` calls `add()`?
- Read source code?
- Trial and error?
- This is the encapsulation leak!

### Experiment 2: Implementation Independence
Create an InstrumentedSet wrapping:
1. HashSet
2. TreeSet
3. LinkedHashSet
4. Collections.synchronizedSet(...)

All work with composition. Inheritance would require four classes!

---

## 💭 Reflection Prompts

1. **When IS inheritance appropriate?**
   - Is-a relationship that's truly substitutable
   - Designed for inheritance (documented, protected hooks)

2. **Guava's Forwarding* classes:**
   - How does Google Guava implement this pattern?
   - What does `ForwardingCollection` provide?

3. **Performance impact:**
   - Extra method call layer — does it matter?
   - JIT inlining to the rescue?

4. **Testing differences:**
   - How does composition improve testability?
   - Can you inject a mock Set?

---

## 🔗 Related Labs

- **LAB19** (Design for Inheritance) — When inheritance is intended
- **LAB20** (Interfaces vs Abstract Classes) — Design choices

---

## 📚 Reference

- Effective Java, Item 18: "Favor composition over inheritance"
- Key insight: Unlike inheritance, composition doesn't expose internal details
