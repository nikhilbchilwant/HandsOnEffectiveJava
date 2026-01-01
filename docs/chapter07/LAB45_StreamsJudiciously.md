# LAB45: Use Streams Judiciously (Item 45)

## 🎯 Learning Objective

Learn when streams help vs hurt code clarity, and understand the trade-offs
between imperative and functional styles.

---

## 📖 Scenario

A junior developer has converted an entire codebase to use streams "because 
streams are more modern." Some conversions improved the code, but many made 
it worse — harder to read, debug, and maintain.

Your task is to identify which uses are appropriate and which should be 
reverted to imperative style.

---

## 📁 Files to Examine

**Flawed Implementation:**
```
flawed-implementations/src/main/java/effectivejava/flawed/chapter07/
├── lab45/
│   ├── AnagramGroups.java        # Good use of streams - keep
│   ├── CartesianProduct.java     # Bad use - too complex, revert
│   ├── MersennePrimes.java       # Mixed - could go either way
│   ├── CardDeck.java             # Bad - nested flatMap confusion
│   ├── WordFrequency.java        # Imperative - convert to stream
│   └── StreamDecisionsDemo.java  # Compare both styles
```

---

## 🔴 What's Wrong?

Study each implementation and classify:

1. **Stream-hostile operations:**
   - Local variables modification (streams require final/effectively final)
   - Return/break/continue from enclosing method
   - Checked exceptions

2. **Stream-friendly operations:**
   - Transform elements
   - Filter elements
   - Combine elements (reduce/collect)
   - Group/partition data

3. **Readability threshold:**
   - When does the pipeline become too long?
   - When are nested flatMaps confusing?

---

## 📋 Your Task

Review and refactor in:
```
refactored-solutions/src/main/java/effectivejava/refactored/chapter07/lab45/
```

### Requirements:

1. **Keep stream**: AnagramGroups — grouping is stream-natural

2. **Revert to loops**: CartesianProduct — nested generation is clearer with loops

3. **Keep stream with cleanup**: MersennePrimes — simplify the pipeline

4. **Revert to loops**: CardDeck initialization — nested flatMap is confusing

5. **Convert to stream**: WordFrequency — counting/grouping is stream-natural

### Guideline (rule of thumb):
- 1-3 stream operations: usually good
- 4-6: scrutinize carefully
- 7+: strongly consider refactoring

---

## ✅ Validation Steps

### Before Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter07Lab45BeforeTest"
```

All implementations should produce correct results (functionality isn't broken).
But review TIME to understand each implementation.

### After Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter07Lab45AfterTest"
```

Your code should:
- [ ] Pass all correctness tests
- [ ] Have improved readability (measure: time to understand)
- [ ] Have maintainable structure

---

## 🧪 Experiments

### Experiment 1: Readability Test
1. Show a colleague the CartesianProduct stream version
2. Time how long it takes them to explain it
3. Repeat with loop version
4. Compare understanding times

### Experiment 2: Debugging Comparison
1. Introduce a bug in the stream pipeline
2. Try to debug it
3. Introduce equivalent bug in loop version
4. Compare debugging experience

### Experiment 3: Performance Check
- Are the stream versions slower?
- When does it matter?
- Profile with JMH

---

## 💭 Reflection Prompts

1. **What makes code readable?**
   - Is "familiar" the same as "readable"?
   - Will streams become more readable as familiarity grows?

2. **Block lambdas smell:**
   - Why are multi-statement lambdas a code smell?
   - When might they be acceptable?

3. **Side effects in streams:**
   - Why are side effects discouraged?
   - Connection to parallel streams?

4. **Method references vs lambdas:**
   - When is each clearer?
   - Does it depend on context?

5. **Iterative style advantages:**
   - What can loops do that streams can't?
   - What's stream's advantage?

---

## 🔗 Related Labs

- **LAB42** (Lambdas vs Anonymous) — Lambda basics
- **LAB46** (Side-Effect-Free) — Stream purity
- **LAB48** (Parallel Streams) — When streams scale

---

## 📚 Reference

- Effective Java, Item 45: "Use streams judiciously"
- Key insight: Overusing streams makes programs hard to read and maintain
