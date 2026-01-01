# LAB50: Make Defensive Copies When Needed (Item 50)

## 🎯 Learning Objective

Understand when mutable arguments and return values must be copied to 
maintain class invariants and prevent external corruption.

---

## 📖 Scenario

A **Period** class representing a time interval has been found to have 
security vulnerabilities: clients can mutate the start and end dates 
after construction, violating the invariant that start <= end.

---

## 📁 Files to Examine

**Flawed Implementation:**
```
flawed-implementations/src/main/java/effectivejava/flawed/chapter08/
├── lab50/
│   ├── Period.java            # No defensive copies
│   ├── Attack.java            # Shows the vulnerability
│   └── MutableFieldsDemo.java # General pattern
```

---

## 🔴 What's Wrong?

Study Period and identify:

1. **Constructor stores mutable references directly**
2. **Getters return internal mutable objects**
3. **Class invariant (start <= end) can be broken**
4. **TOCTOU attacks possible** (Time-of-check to time-of-use)

---

## 📋 Your Task

Fix Period with defensive copies in:
```
refactored-solutions/src/main/java/effectivejava/refactored/chapter08/lab50/
```

### Requirements:

1. **Copy mutable parameters** in constructor:
   - Copy BEFORE validation (prevents TOCTOU)
   - Use copy constructors or static factories, not clone()

2. **Copy mutable returns** in getters:
   - Return copies, not originals

3. **Consider immutable alternatives:**
   - `Instant`, `LocalDateTime` instead of `Date`

---

## ✅ Validation Steps

### Before Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter08Lab50BeforeTest"
```

- Test `attack_modifyAfterConstruction` — Invariant broken!

### After Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter08Lab50AfterTest"
```

- [ ] Pass `invariantProtected_afterConstruction`
- [ ] Pass `invariantProtected_throughGetter`
- [ ] Pass `tocttouAttack_prevented`

---

## 🧪 Experiments

### Experiment: The Attack

```java
Date start = new Date();
Date end = new Date();
Period p = new Period(start, end);  // Valid

end.setTime(start.getTime() - 86400000);  // Modify end!
// Now p.end is BEFORE p.start - invariant broken!
```

---

## 💭 Reflection Prompts

1. **Why copy before validation?**
   - What is Time-of-Check to Time-of-Use (TOCTOU)?

2. **Why avoid clone() for copies?**
   - What if Date were non-final?

3. **Performance cost:**
   - When is the copy overhead acceptable?
   - When should you use immutable types instead?

---

## 📚 Reference

- Effective Java, Item 50: "Make defensive copies when needed"
