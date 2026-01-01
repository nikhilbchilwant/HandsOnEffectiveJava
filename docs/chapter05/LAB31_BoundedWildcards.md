# LAB31: Use Bounded Wildcards to Increase API Flexibility (Item 31)

## 🎯 Learning Objective

Master PECS (Producer Extends, Consumer Super) to create flexible generic APIs
that work with type hierarchies while maintaining type safety.

---

## 📖 Scenario

You're building a **collection utility library** with methods like `addAll`, 
`copyTo`, and `max`. The current implementations are too restrictive — they 
only work with exact type matches, rejecting perfectly valid inputs.

---

## 📁 Files to Examine

**Flawed Implementation:**
```
flawed-implementations/src/main/java/effectivejava/flawed/chapter05/
├── lab31/
│   ├── Stack.java              # pushAll/popAll too restrictive
│   ├── CollectionUtils.java    # Utility methods lack flexibility
│   ├── NumberProcessor.java    # Works only with exact Number
│   └── WildcardDemo.java       # Shows the restrictions
```

---

## 🔴 What's Wrong?

Study the implementations and identify:

1. **Invariant generics** — `Stack<Number>` won't accept `Integer`
2. **Producer parameter needs `extends`** — Can't add subtypes
3. **Consumer parameter needs `super`** — Can't accept supertypes
4. **Overly specific return types** — Reduces usability

---

## 📋 Your Task

Apply PECS correctly in:
```
refactored-solutions/src/main/java/effectivejava/refactored/chapter05/lab31/
```

### PECS Rule:
- **Producer Extends**: If parameter provides T values → `<? extends T>`
- **Consumer Super**: If parameter consumes T values → `<? super T>`

### Requirements:

1. **Fix Stack:**
   - `pushAll(Iterable<? extends E> src)` — src PRODUCES E values
   - `popAll(Collection<? super E> dst)` — dst CONSUMES E values

2. **Fix CollectionUtils:**
   - `max()` should work with `List<Integer>` when comparing `Number`
   - `copy()` should work with compatible source/destination types

3. **Fix NumberProcessor:**
   - Should process any subtype of Number through a consumer

4. **Remember: Don't use wildcards in return types**

---

## ✅ Validation Steps

### Before Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter05Lab31BeforeTest"
```

Observe:
```java
Stack<Number> stack = new Stack<>();
Iterable<Integer> integers = List.of(1, 2, 3);
stack.pushAll(integers);  // FAILS to compile!
```

### After Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter05Lab31AfterTest"
```

Your code should:
- [ ] Pass `pushAll_acceptsSubtypes`
- [ ] Pass `popAll_acceptsSupertypes`
- [ ] Pass `max_worksWithSubtypes`
- [ ] Pass `copy_flexibleSourceAndDest`

---

## 🧪 Experiments

### Experiment 1: PECS Decision Tree
For each method below, decide extends/super/both/neither:
```java
void addTo(Collection<???> target, T element)  // target CONSUMES
T getFrom(Supplier<???> source)                // source PRODUCES
void transfer(List<???> from, List<???> to)    // from PRODUCES, to CONSUMES
```

### Experiment 2: Comparable Bounding
Why is this the recommended signature for max?
```java
<T extends Comparable<? super T>> T max(Collection<? extends T> c)
```
Test with classes where `Comparable` is defined on a superclass.

### Experiment 3: Nested Wildcards
Understand why this is over-complicated and rarely needed:
```java
List<? extends List<? extends Number>>
```

---

## 💭 Reflection Prompts

1. **Why PECS and not the reverse?**
   - Think about covariance and contravariance
   - How does each direction of the hierarchy flow?

2. **When does PECS not apply?**
   - What if T is both produced AND consumed?
   - Answer: Use exact type, not wildcard

3. **Wildcards and readability:**
   - Do wildcards make APIs harder to understand?
   - When is the complexity worth it?

4. **Capture helper methods:**
   - What is "wildcard capture"?
   - When do you need a private helper?

5. **Type inference evolution:**
   - How has Java's type inference improved?
   - Does modern Java reduce the need for wildcards?

---

## 🔗 Related Labs

- **LAB26** (Raw Types) — Why generics matter
- **LAB29** (Generic Types) — Creating generic classes
- **LAB30** (Generic Methods) — Generic method signatures

---

## 📚 Reference

- Effective Java, Item 31: "Use bounded wildcards to increase API flexibility"
- PECS mnemonic: Producer-Extends, Consumer-Super
