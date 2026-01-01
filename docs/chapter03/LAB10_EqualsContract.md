# LAB10: Obey the General Contract When Overriding equals (Item 10)

## 🎯 Learning Objective

Master the equals() contract: reflexive, symmetric, transitive, consistent, 
and non-null. Learn to identify violations and their consequences.

---

## 📖 Scenario

You're building a **geometry library** with various shape classes. The team 
has been implementing equals() methods without considering the full contract, 
leading to subtle bugs in collections, deduplication, and business logic.

---

## 📁 Files to Examine

**Flawed Implementation:**
```
flawed-implementations/src/main/java/effectivejava/flawed/chapter03/
├── lab10/
│   ├── Point.java                    # Broken symmetry in subclass
│   ├── ColorPoint.java               # Violates transitivity
│   ├── CaseInsensitiveString.java    # Violates symmetry with String
│   ├── Counter.java                  # Mutable field in equals
│   └── EqualsContractDemo.java       # Shows the violations
```

---

## 🔴 What's Wrong?

Study each class and identify contract violations:

1. **Symmetry violation** — `a.equals(b)` but not `b.equals(a)`
2. **Transitivity violation** — `a.equals(b)` and `b.equals(c)` but not `a.equals(c)`
3. **Consistency violation** — equals() result changes based on mutable state
4. **Interoperability issues** — equals() with unrelated types
5. **Liskov Substitution violation** — Subclass breaks parent's equals

---

## 📋 Your Task

Fix the equals() implementations. Create in:
```
refactored-solutions/src/main/java/effectivejava/refactored/chapter03/lab10/
```

### Requirements:

1. **Fix CaseInsensitiveString:**
   - Should NOT try to interoperate with String
   - Only compare with same type

2. **Fix Point/ColorPoint hierarchy:**
   - Option A: Use composition instead of inheritance
   - Option B: Use `getClass()` instead of `instanceof` (with trade-offs)

3. **Fix all equals() methods to be:**
   - Reflexive: `x.equals(x)` returns true
   - Symmetric: `x.equals(y) == y.equals(x)`
   - Transitive: `x.equals(y) && y.equals(z)` implies `x.equals(z)`
   - Consistent: Same result if objects unchanged
   - Non-null: `x.equals(null)` returns false

4. **Implement matching hashCode() methods** (covered more in LAB11)

### Constraints:
- Must work correctly in HashSet, HashMap
- Must handle null gracefully
- Document any design trade-offs

---

## ✅ Validation Steps

### Before Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter03Lab10BeforeTest"
```

Observe contract violations:
- Test `symmetry_caseInsensitiveVsString` — FAILS (asymmetric)
- Test `transitivity_colorPointHierarchy` — FAILS (not transitive)
- Test `hashSetBehavior_brokenWithBadEquals` — Shows collection bugs

### After Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter03Lab10AfterTest"
```

Your code should:
- [ ] Pass `reflexivity`
- [ ] Pass `symmetry_sameType`
- [ ] Pass `transitivity`
- [ ] Pass `consistency`
- [ ] Pass `nonNullity`
- [ ] Pass `worksInHashSet`
- [ ] Pass `worksInHashMap`

---

## 🧪 Experiments

### Experiment 1: Collection Behavior
1. Add a Point to a HashSet
2. Add a ColorPoint (same x,y, different color) to the same set
3. What happens with the flawed code?
4. What happens with correct code?

### Experiment 2: Map Keys
1. Put a value with Point key
2. Try to get using ColorPoint key (same x,y)
3. Observe the difference between `instanceof` and `getClass()` approaches

### Experiment 3: Symmetry Check
Write code that systematically checks symmetry:
```java
void assertSymmetric(Object a, Object b) {
    if (a.equals(b) != b.equals(a)) throw new AssertionError();
}
```

---

## 💭 Reflection Prompts

1. **Why is inheritance problematic for value equality?**
   - What does "semantic equivalence" mean?
   - How does Liskov Substitution apply?

2. **instanceof vs getClass():**
   - What are the trade-offs?
   - When might you choose one over the other?
   - Book's recommendation?

3. **Why can you extend an abstract class safely?**
   - If Point were abstract, what changes?

4. **Composition vs inheritance for equals:**
   - How does ColorPoint-as-composition work?
   - What's the API impact?

5. **IDE-generated equals:**
   - Does your IDE generate correct equals?
   - What does it miss?

---

## 🔗 Related Labs

- **LAB11** (hashCode) — MUST be consistent with equals
- **LAB14** (Comparable) — Similar contract issues
- **LAB17** (Immutability) — Simplifies equals

---

## 📚 Reference

- Effective Java, Item 10: "Obey the general contract when overriding equals"
- Key insight: There's no way to extend an instantiable class and add a value component while preserving the equals contract
