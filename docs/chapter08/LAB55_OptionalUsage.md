# LAB55: Return Optionals Judiciously (Item 55)

## 🎯 Learning Objective

Master the correct use of Optional as a return type: when it's appropriate,
when it's not, and common anti-patterns to avoid.

---

## 📖 Scenario

A data access layer uses null returns, Optional as fields, Optional in 
collections, and other misuses. The API is confusing and error-prone.

---

## 📁 Files to Examine

**Flawed Implementation:**
```
flawed-implementations/src/main/java/effectivejava/flawed/chapter08/
├── lab55/
│   ├── UserRepository.java        # Mixed null and Optional
│   ├── OptionalMisuses.java       # Various anti-patterns
│   └── NullCheckingHell.java      # What we're trying to avoid
```

---

## 🔴 What's Wrong?

Study the implementations and identify:

1. **Optional as field** — Anti-pattern, wastes memory
2. **Optional in collections** — Never do this
3. **Optional.of() for nullable** — Throws NPE
4. **Unwrapping without isPresent** — NoSuchElementException
5. **Overusing Optional** — Not for every return type

---

## 📋 Your Task

Refactor correctly in:
```
refactored-solutions/src/main/java/effectivejava/refactored/chapter08/lab55/
```

### Guidelines:

1. **Use Optional when:**
   - Return value may legitimately be absent
   - Client must handle absence explicitly
   - It improves API clarity

2. **Don't use Optional when:**
   - In container types (lists, arrays, maps)
   - As instance fields
   - As method parameters
   - For primitive types (use OptionalInt/Long/Double)

3. **Best practices:**
   - `Optional.ofNullable()` for nullable sources
   - `orElse()`, `orElseGet()`, `orElseThrow()` for unwrapping  
   - Stream integration with `flatMap()`

---

## ✅ Validation Steps

### Before Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter08Lab55BeforeTest"
```

### After Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter08Lab55AfterTest"
```

- [ ] Pass `optionalReturnsForLegitimateAbsence`
- [ ] Pass `noOptionalFields`
- [ ] Pass `noOptionalParameters`
- [ ] Pass `fluentUnwrapping`

---

## 🧪 Experiments

### Experiment 1: Performance Comparison
```java
Optional.of(value)  vs  value  // Extra object allocation?
```
When does this matter? Profile with high volume.

### Experiment 2: Stream Integration
```java
stream.map(this::findById)       // Stream<Optional<User>>
      .flatMap(Optional::stream) // Stream<User>, empties filtered out
```

---

## 💭 Reflection Prompts

1. **Optional vs null:** When is null actually acceptable?

2. **API evolution:** How do you add Optional to existing code?

3. **Kotlin's approach:** How does Kotlin handle nullability differently?

---

## 🔗 Related Labs

- **LAB54** (Empty Collections) — Similar API friendliness theme

---

## 📚 Reference

- Effective Java, Item 55: "Return optionals judiciously"
