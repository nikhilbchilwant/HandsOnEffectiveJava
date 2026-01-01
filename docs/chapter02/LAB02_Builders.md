# LAB02: Builder Pattern for Many Parameters (Item 2)

## 🎯 Learning Objective

Master the Builder pattern for objects with many optional parameters, 
understanding when it outshines telescoping constructors and JavaBeans patterns.

---

## 📖 Scenario

You're developing a **Notification Service** that sends multi-channel notifications 
(email, SMS, push, Slack). Each notification type has numerous optional settings 
(priority, scheduling, retry policies, personalization, etc.). The current 
implementation uses the JavaBeans pattern (setters), which allows object construction 
in an inconsistent state and prevents immutability.

---

## 📁 Files to Examine

**Flawed Implementation:**
```
flawed-implementations/src/main/java/effectivejava/flawed/chapter02/
├── lab02/
│   ├── Notification.java             # JavaBeans pattern with setters
│   ├── NotificationService.java      # Sends notifications
│   └── NotificationDemo.java         # Shows the problems
```

---

## 🔴 What's Wrong?

Study the `Notification` class and identify:

1. **Inconsistent state** — Object is valid before all required fields are set?
2. **No immutability** — Can be modified after construction
3. **Thread-safety issues** — Mutable state without synchronization
4. **Hidden requirements** — Which fields are actually required?
5. **No validation** — Invalid combinations slip through

---

## 📋 Your Task

Refactor using the Builder pattern. Create your implementation in:
```
refactored-solutions/src/main/java/effectivejava/refactored/chapter02/lab02/
```

### Requirements:

1. **Create a nested static Builder class** with:
   - Required parameters in Builder constructor
   - Optional parameters via fluent setters returning `this`
   - `build()` method that constructs immutable `Notification`

2. **Make Notification immutable:**
   - Private constructor taking only the Builder
   - All fields `final`
   - No setters

3. **Add validation in `build()`:**
   - Required: recipient, channel, message
   - Validate: priority 1-5, scheduleTime in future if set

4. **Support fluent chaining:**
```java
Notification n = Notification.builder("user@example.com", Channel.EMAIL)
    .message("Hello!")
    .priority(Priority.HIGH)
    .retryCount(3)
    .build();
```

### Constraints:
- Do NOT skip validation
- Make defensive copies where needed
- Consider a hierarchical builder for channel-specific options (bonus)

---

## ✅ Validation Steps

### Before Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter02Lab02BeforeTest"
```

Observe:
- Test `inconsistentState_allowsPartiallyConstructedObjects` — demonstrates the flaw
- Test `mutability_objectCanBeChangedAfterCreation` — shows immutability violation
- Test `noValidation_acceptsInvalidConfigurations` — invalid data slips through

### After Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter02Lab02AfterTest"
```

Your code should:
- [ ] Pass `builderEnforcesRequiredFields`
- [ ] Pass `immutability_noSettersAvailable`
- [ ] Pass `validation_rejectsInvalidPriority`
- [ ] Pass `fluentApi_readableConstruction`
- [ ] Pass `threadSafety_immutableObject`

---

## 🧪 Experiments

### Experiment 1: Compare Readability
Write the same notification configuration using:
1. Telescoping constructors (if you created them)
2. JavaBeans setters (flawed version)
3. Builder (your refactored version)

Which is most readable? Most maintainable?

### Experiment 2: API Evolution
Add a new optional field `customHeaders` (Map<String, String>).
- How hard is it to add with JavaBeans? 
- How hard with Builder?
- Did you break any existing client code?

---

## 💭 Reflection Prompts

1. **When is Builder overkill?**
   - What's the minimum number of parameters that justifies a Builder?
   - Would you use it for a `Point(x, y)` class?

2. **Builder vs. Static Factories:**
   - When would you choose one over the other?
   - Can they be combined?

3. **Builder and Inheritance:**
   - How would you create a Builder for a class hierarchy?
   - Research: simulated self-type idiom with recursive generics

4. **Cost of Builder:**
   - What's the performance overhead?
   - When might this matter?

5. **Records and Builders:**
   - Java 16+ records are immutable. Do they eliminate the need for builders?
   - When might you still want a Builder for a record-like class?

---

## 🔗 Related Labs

- **LAB01** (Static Factories) — Complementary creational pattern
- **LAB17** (Immutability) — Deep dive into immutable objects
- **LAB50** (Defensive Copies) — Protecting mutable components

---

## 📚 Reference

- Effective Java, Item 2: "Consider a builder when faced with many constructor parameters"
- Compare to: Telescoping constructors, JavaBeans, Kotlin data classes, Lombok @Builder
