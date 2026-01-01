# LAB85: Prefer Alternatives to Java Serialization (Item 85)

## 🎯 Learning Objective

Understand the security risks of Java serialization and learn to use 
safer alternatives like JSON, Protocol Buffers, or other cross-platform formats.

---

## 📖 Scenario

Your system uses Java serialization for persisting objects and RPC. Recent 
security audits have flagged this as a critical vulnerability. You need to 
migrate to safer alternatives.

---

## 📁 Files to Examine

**Flawed Implementation:**
```
flawed-implementations/src/main/java/effectivejava/flawed/chapter12/
├── lab85/
│   ├── UserData.java              # Uses Java Serializable
│   ├── SerializationService.java  # Serializes/deserializes objects
│   ├── VulnerabilityDemo.java     # Shows the risks
│   └── GadgetChainExample.java    # Simplified attack vector
```

---

## 🔴 What's Wrong?

Study the serialization usage and understand:

1. **Gadget chains** — Deserialization can trigger arbitrary code
2. **Attack surface** — Every Serializable class is a potential exploit
3. **Backwards compatibility burden** — Serial version UID management
4. **Cross-platform issues** — Java-specific format
5. **Testing difficulty** — Hard to validate safety

---

## 📋 Your Task

Migrate to safer alternatives in:
```
refactored-solutions/src/main/java/effectivejava/refactored/chapter12/lab85/
```

### Requirements:

1. **Replace Java serialization with JSON (using Gson or Jackson):**
   - Explicit field mapping
   - No arbitrary code execution
   - Human-readable format

2. **For performance-critical paths, consider:**
   - Protocol Buffers (cross-platform, schema-based)
   - Explicit serialization methods

3. **If you MUST use Java serialization:**
   - Whitelist allowed classes
   - Use ObjectInputFilter (Java 9+)

4. **Verify:** No ObjectInputStream on untrusted data

---

## ✅ Validation Steps

### Before Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter12Lab85BeforeTest"
```

- Understand the vulnerability (don't actually exploit!)
- See how easy it is to construct malicious payloads

### After Refactoring:
```bash
mvn test -pl tests-validation -Dtest="Chapter12Lab85AfterTest"
```

- [ ] Pass `noObjectInputStreamOnUntrustedData`
- [ ] Pass `jsonSerialization_works`
- [ ] Pass `crossPlatformFormat`
- [ ] Pass `humanReadable` (for JSON path)

---

## 🧪 Experiments

### Experiment 1: Gadget Chain Understanding
Research the "ysoserial" tool (don't run on production!).
Understand how deserialization can trigger:
- File writes
- Process execution
- Network connections

### Experiment 2: ObjectInputFilter
If you must use serialization, implement a filter:
```java
ObjectInputFilter filter = info -> {
    if (info.serialClass() != null) {
        String name = info.serialClass().getName();
        if (!ALLOWED_CLASSES.contains(name)) {
            return Status.REJECTED;
        }
    }
    return Status.ALLOWED;
};
```

---

## 💭 Reflection Prompts

1. **Why is deserialization so dangerous?**
   - What methods get called during deserialization?
   - How do gadget chains work?

2. **JSON vs Protocol Buffers:**
   - When would you choose each?
   - Performance vs human-readability trade-off

3. **Legacy system migration:**
   - How do you migrate existing serialized data?
   - Versioning strategies?

4. **When might you still need Java serialization?**
   - RMI? JMX?
   - How to minimize risk?

---

## 🔗 Related Labs

- **LAB86** (Serializable Risks) — More on implementation risks
- **LAB88** (Defensive readObject) — If you must use it
- **LAB90** (Serialization Proxies) — Safest form if required

---

## 📚 Reference

- Effective Java, Item 85: "Prefer alternatives to Java serialization"
- Key insight: The best way to avoid serialization exploits is not to deserialize
