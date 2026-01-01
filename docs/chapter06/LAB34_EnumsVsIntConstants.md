# LAB34: Use Enums Instead of int Constants (Item 34)

## 🎯 Learning Objective

Understand the type safety, namespace, and behavior advantages of enums
over the int enum pattern and String constant patterns.

---

## 📖 Scenario

A legacy codebase uses `public static final int` constants to represent 
planet types, days of week, and operation types. This leads to type confusion, 
no namespace, and scattered switch statements.

---

## 📁 Files to Examine

**Flawed Implementation:**
```
flawed-implementations/src/main/java/effectivejava/flawed/chapter06/
├── lab34/
│   ├── PlanetConstants.java       # int constants for planets
│   ├── OperationConstants.java    # int constants for operations
│   ├── AppleOrangeConfusion.java  # Type confusion demo
│   └── ConstantPatternDemo.java   # Shows the problems
```

---

## 🔴 What's Wrong?

Study the int enum pattern and identify:

1. **No type safety**: Can pass APPLE where ORANGE expected
2. **No namespace**: All constants are global, risk of collision
3. **Brittleness**: Client code embeds values, recompile required
4. **No meaningful toString**: Just prints the int value
5. **No iteration**: Can't enumerate all values
6. **No behavior**: Can't add methods or fields to each constant

---

## 📋 Your Task

Convert to proper enums in:
```
refactored-solutions/src/main/java/effectivejava/refactored/chapter06/lab34/
```

### Requirements:

1. **Create Planet enum** with:
   - Fields: mass, radius
   - Method: surfaceGravity()
   - Method: surfaceWeight(double mass)

2. **Create Operation enum** with:
   - Strategy pattern: each constant has its own apply() implementation
   - Use abstract method or switch expression

3. **Verify:** 
   - Type safety (can't pass wrong enum type)
   - Namespace (Planet.EARTH vs Day.MONDAY)
   - Meaningful toString()

---

## ✅ Validation Steps

### Before Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter06Lab34BeforeTest"
```

- Test `typeSafety_violated` — Can compare apples to oranges literally
- Test `noNamespace_collision` — Global constants clash
- Test `noIeration_cannotEnumerate` — No way to loop over all values

### After Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter06Lab34AfterTest"
```

- [ ] Pass `typeSafety_enforced` — Compile error for wrong type
- [ ] Pass `hasNamespace` — Planet.MARS vs Month.MARCH are distinct
- [ ] Pass `canIterate_values` — Planet.values() works
- [ ] Pass `hasBehavior_surfaceGravity` — Methods on enum

---

## 🧪 Experiments

### Experiment 1: toString Quality
```java
System.out.println(PLANET_EARTH);  // prints "2" 😕
System.out.println(Planet.EARTH);  // prints "EARTH" 😊
```

### Experiment 2: Switch Exhaustiveness
Use enhanced switch with enum:
```java
String description = switch (planet) {
    case MERCURY -> "Closest to sun";
    case VENUS -> "Hottest planet";
    // Compiler warns if you miss one!
};
```

### Experiment 3: Ordinal Fragility
Add a new planet constant. What breaks?
- With int constants: ordering, documentation, client assumptions
- With enum: values() updates automatically, ordinal is fragile (LAB35)

---

## 💭 Reflection Prompts

1. **When might int constants still be appropriate?**
   - Interoperability with C libraries?
   - Wire protocols?

2. **Constant-specific method implementations:**
   - When is this cleaner than switch?
   - How does it relate to Strategy pattern?

3. **EnumSet and EnumMap:**
   - Preview: LAB36 and LAB37 explore these

4. **Serialization:**
   - How are enums serialized differently?
   - Why is this more robust?

---

## 🔗 Related Labs

- **LAB35** (Instance Fields) — Don't derive from ordinal()
- **LAB36** (EnumSet) — Efficient set operations
- **LAB37** (EnumMap) — Efficient enum-keyed maps

---

## 📚 Reference

- Effective Java, Item 34: "Use enums instead of int constants"
- Key insight: Java's enum types are full-fledged classes
