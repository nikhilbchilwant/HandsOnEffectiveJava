# LAB09: Prefer Try-With-Resources to Try-Finally (Item 9)

## 🎯 Learning Objective

Understand why try-with-resources is superior to try-finally for resource 
management, especially with multiple resources or exceptions.

---

## 📖 Scenario

A file processing utility has resource leaks and exception masking bugs due 
to using try-finally incorrectly. When multiple resources are involved, the 
cleanup code becomes a nested nightmare.

---

## 📁 Files to Examine

**Flawed Implementation:**
```
flawed-implementations/src/main/java/effectivejava/flawed/chapter02/
├── lab09/
│   ├── FileCopier.java          # Try-finally with multiple resources
│   ├── DatabaseReader.java      # Exception masking problem
│   └── ResourceLeakDemo.java    # Shows the issues
```

---

## 🔴 What's Wrong?

Study the implementations and identify:

1. **Nested try-finally** — Hard to read, error-prone
2. **Exception masking** — close() exception hides original exception
3. **Resource leaks** — If open succeeds but close throws, second resource not closed
4. **Verbose code** — Lots of boilerplate for simple operations

---

## 📋 Your Task

Refactor to use try-with-resources in:
```
refactored-solutions/src/main/java/effectivejava/refactored/chapter02/lab09/
```

### Requirements:

1. **Convert FileCopier** to single try-with-resources with multiple resources

2. **Fix exception handling:**
   - Original exception is preserved
   - Close exceptions are "suppressed" (accessible via `getSuppressed()`)

3. **Implement AutoCloseable** for any custom resources

4. **Verify:** All resources closed even when exceptions occur

---

## ✅ Validation Steps

### Before Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter02Lab09BeforeTest"
```

- `exceptionMasking_originalLost` — Original exception hidden by close() exception
- `nestedTryFinally_verbose` — Count the lines of cleanup code

### After Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter02Lab09AfterTest"
```

- [ ] Pass `tryWithResources_preservesOriginalException`
- [ ] Pass `suppressedExceptions_accessible`
- [ ] Pass `allResourcesClosed_evenWithExceptions`
- [ ] Pass `codeIsSimpler` — Significantly fewer lines

---

## 🧪 Experiments

### Experiment 1: Suppressed Exceptions
```java
try (ProblematicResource r = new ProblematicResource()) {
    throw new IOException("Original");
} catch (IOException e) {
    System.out.println("Caught: " + e.getMessage());
    for (Throwable t : e.getSuppressed()) {
        System.out.println("Suppressed: " + t.getMessage());
    }
}
```

### Experiment 2: Resource Order
With try-with-resources, resources are closed in reverse order of declaration.
Verify this with logging in close() methods.

---

## 💭 Reflection Prompts

1. **Why reverse close order?**
   - Think about dependency between resources
   - LIFO vs FIFO for cleanup

2. **What about resources without AutoCloseable?**
   - Manual management still needed
   - Wrapper patterns

3. **Null-safe close:**
   - What if resource is null?
   - try-with-resources handles this!

---

## 🔗 Related Labs

- **LAB07** (Obsolete References) — Memory management
- **LAB08** (Finalizers/Cleaners) — Why not to use them

---

## 📚 Reference

- Effective Java, Item 9: "Prefer try-with-resources to try-finally"
- Key insight: Shorter, clearer, and generates better diagnostics
