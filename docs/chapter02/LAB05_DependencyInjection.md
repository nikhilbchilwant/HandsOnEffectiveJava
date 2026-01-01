# LAB05: Prefer Dependency Injection to Hard-wiring Resources (Item 5)

## 🎯 Learning Objective

Understand why hard-coded dependencies make classes inflexible and untestable, 
and learn to refactor toward dependency injection patterns.

---

## 📖 Scenario

You're working on a **Spell Checker Service** used across multiple products. 
The current implementation hard-wires a single dictionary, making it impossible 
to support multiple languages, customize for different domains (medical, legal), 
or test in isolation.

---

## 📁 Files to Examine

**Flawed Implementation:**
```
flawed-implementations/src/main/java/effectivejava/flawed/chapter02/
├── lab05/
│   ├── SpellChecker.java            # Hard-wired dictionary (utility class style)
│   ├── SpellCheckerSingleton.java   # Hard-wired dictionary (singleton style)
│   ├── EnglishDictionary.java       # Concrete dictionary
│   └── SpellCheckerClient.java      # Shows the problems
```

---

## 🔴 What's Wrong?

Study both spell checker implementations and identify:

1. **Inflexibility** — Can't swap dictionaries at runtime
2. **Untestable** — Can't inject mock dictionary for testing
3. **Single behavior** — Can't support multiple languages/domains
4. **Hidden dependency** — Dependency is buried inside the class
5. **Tight coupling** — SpellChecker knows about specific dictionary

---

## 📋 Your Task

Refactor using dependency injection. Create your implementation in:
```
refactored-solutions/src/main/java/effectivejava/refactored/chapter02/lab05/
```

### Requirements:

1. **Create a Dictionary interface:**
   - `boolean contains(String word)`
   - `List<String> suggestions(String misspelled)`

2. **Refactor SpellChecker to accept Dictionary via constructor:**
   - Remove static creation of dictionary
   - Store dictionary as final field
   - Validate that dictionary is not null

3. **Create multiple Dictionary implementations:**
   - `EnglishDictionary`
   - `MedicalDictionary` (domain-specific)
   - `TestDictionary` (for testing, with known words)

4. **Support factory pattern variant:**
   - `SpellChecker(Supplier<Dictionary> dictionaryFactory)`
   - Useful for dictionaries that are expensive to create

### Constraints:
- Dictionary must be immutable (or at least effectively immutable)
- SpellChecker must be thread-safe if dictionary is thread-safe

---

## ✅ Validation Steps

### Before Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter02Lab05BeforeTest"
```

Observe:
- Test `cannotTestWithMockDictionary` — Demonstrates testing limitation
- Test `cannotSupportMultipleLanguages` — Shows inflexibility
- Test `hiddenDependency_notExplicit` — Coupling issues

### After Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter02Lab05AfterTest"
```

Your code should:
- [ ] Pass `canInjectMockDictionary`
- [ ] Pass `supportsDifferentLanguages`
- [ ] Pass `dependencyIsExplicit`
- [ ] Pass `factoryVariantWorks`

---

## 🧪 Experiments

### Experiment 1: Testing Improvement
Write a unit test for SpellChecker:
1. With the flawed version — how do you test it?
2. With your refactored version — inject a mock, verify behavior

### Experiment 2: Performance Comparison
1. Create a dictionary factory that loads from a file (slow)
2. Compare: constructor injection vs Supplier<Dictionary> injection
3. When does lazy loading matter?

---

## 💭 Reflection Prompts

1. **Static utility class vs dependency injection:**
   - When are static utilities acceptable?
   - What's the cost of "everything takes dependencies"?

2. **How does this relate to the "D" in SOLID?**
   - Dependency Inversion Principle
   - High-level modules shouldn't depend on low-level modules

3. **Constructor injection vs other forms:**
   - Setter injection, method injection, interface injection
   - When might you use each?

4. **DI frameworks (Spring, Guice):**
   - What do they add beyond manual injection?
   - When is a framework overkill?

5. **Factories and Suppliers:**
   - When would you inject a factory instead of the object itself?

---

## 🔗 Related Labs

- **LAB03** (Singletons) — Often used with DI for instance control
- **LAB18** (Composition over Inheritance) — Related composability theme
- **LAB64** (Interface References) — Program to interfaces

---

## 📚 Reference

- Effective Java, Item 5: "Prefer dependency injection to hardwiring resources"
- Key insight: Pass resources (or factories) to constructors for flexibility and testability
