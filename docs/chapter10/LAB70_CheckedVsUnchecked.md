# LAB70: Use Checked Exceptions for Recoverable Conditions (Item 70)

## 🎯 Learning Objective

Master the decision of when to use checked vs unchecked exceptions,
understanding the impact on API design and client code burden.

---

## 📖 Scenario

You're reviewing a **payment processing library** that uses exceptions 
extensively. The current design makes poor choices about checked vs unchecked,
burdening callers with catching unrecoverable errors while silently allowing
recoverable conditions to crash programs.

---

## 📁 Files to Examine

**Flawed Implementation:**
```
flawed-implementations/src/main/java/effectivejava/flawed/chapter10/
├── lab70/
│   ├── PaymentProcessor.java   # Wrong exception choices
│   ├── PaymentException.java   # Base exception class
│   ├── Exceptions.java         # Various exception types
│   └── PaymentClient.java      # Shows the burden
```

---

## 🔴 What's Wrong?

Study the implementations and identify:

1. **Checked exceptions for unrecoverable conditions:**
   - Programming errors throwing checked exceptions
   - Callers forced to catch and do nothing useful

2. **Unchecked exceptions for recoverable conditions:**
   - Network failures, validation errors silently crashing
   - Callers not prompted to handle failure

3. **Too broad exception types:**
   - `throws Exception` or `throws Throwable`
   - Catching too broadly, hiding bugs

4. **Exception abuse:**
   - Using exceptions for flow control
   - Empty catch blocks

---

## 📋 Your Task

Redesign the exception hierarchy in:
```
refactored-solutions/src/main/java/effectivejava/refactored/chapter10/lab70/
```

### Decision Tree:
1. **Is recovery possible AND expected?** → Checked exception
2. **Is it a programming error?** → Unchecked (RuntimeException)
3. **Is it a JVM error?** → Error (don't throw these)

### Requirements:

1. **Classify each exception correctly:**
   - `InsufficientFundsException` → CHECKED (recoverable: try another payment method)
   - `NullArgumentException` → UNCHECKED (programming error)
   - `InvalidCardNumberException` → Depends! Validation error or programming error?
   - `NetworkTimeoutException` → CHECKED (recoverable: retry)
   - `DuplicateTransactionException` → CHECKED (recoverable: return existing)

2. **Provide recovery information:**
   - Checked exceptions should have methods to help recovery
   - `InsufficientFundsException.getAvailableBalance()`
   - `NetworkTimeoutException.getSuggestedRetryDelay()`

3. **Don't force impossible catches:**
   - If caller can't meaningfully recover, don't force them to catch

---

## ✅ Validation Steps

### Before Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter10Lab70BeforeTest"
```

Observe the API burden:
- Client code full of try-catch for unrecoverable conditions
- Recoverable conditions cause crashes

### After Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter10Lab70AfterTest"
```

Your code should:
- [ ] Pass `checkedExceptions_areRecoverable`
- [ ] Pass `uncheckedExceptions_areProgrammingErrors`
- [ ] Pass `recoveryInformation_availableInException`
- [ ] Demonstrate cleaner client code

---

## 🧪 Experiments

### Experiment 1: API Usability
1. Write client code using the flawed API
2. Count the try-catch blocks
3. Identify which catches can do something useful
4. Rewrite with refactored API

### Experiment 2: Exception Granularity
- What's too specific? `InvalidCardNumberLengthException`?
- What's too broad? `PaymentException`?
- Find the right balance

### Experiment 3: Alternative Returns
For some "exceptions", consider alternatives:
- `Optional<T>` for expected empty results
- `Result<T, E>` pattern (success or error)
- When are exceptions actually appropriate?

---

## 💭 Reflection Prompts

1. **Why the controversy over checked exceptions?**
   - What's the case for them?
   - What's the case against?
   - What does Kotlin/Scala do?

2. **Stream and lambda limitations:**
   - How do checked exceptions interact with lambdas?
   - What patterns help?

3. **Exception translation:**
   - When should low-level exceptions be wrapped?
   - What context should be preserved?

4. **Over-engineering risk:**
   - When are you creating too many exception types?
   - Is `PaymentProcessingException` with subtypes better than many classes?

5. **The caller's perspective:**
   - What can a caller actually DO with each exception?
   - If nothing useful, should it be checked?

---

## 🔗 Related Labs

- **LAB69** (Exceptional Conditions Only) — When to throw at all
- **LAB71** (Avoid Checked Exception Overuse) — Don't overdo it
- **LAB72** (Standard Exceptions) — Reuse existing types

---

## 📚 Reference

- Effective Java, Item 70: "Use checked exceptions for recoverable conditions and runtime exceptions for programming errors"
- Key insight: The decision whether to throw checked or unchecked is one of the most important in API design
