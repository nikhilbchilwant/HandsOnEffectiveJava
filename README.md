# Hands-On Effective Java: Learning by Refactoring

[![Java Version](https://img.shields.io/badge/Java-17+-blue.svg)](https://openjdk.java.net/)
[![Build](https://img.shields.io/badge/Build-Maven-green.svg)](https://maven.apache.org/)

## 🎯 Project Overview

This is a **hands-on learning project** that teaches Joshua Bloch's "Effective Java" principles through 
**implementation, refactoring, and guided experiments** — NOT passive reading.

### Learning Philosophy

> "Tell me and I forget. Teach me and I remember. Involve me and I learn." — Benjamin Franklin

You will **fix the code in place** following TODO markers, run validation tests, and reason about trade-offs.

---

## 📁 Project Structure

```
HandsOnEffectiveJava/
├── pom.xml                           # Parent POM
├── README.md                         # This file
│
├── labs/                             # 🔧 FIX THE CODE HERE!
│   ├── pom.xml
│   └── src/main/java/effectivejava/labs/
│       ├── chapter02/lab01/          # Static factories
│       ├── chapter02/lab02/          # Builder pattern
│       ├── chapter03/lab10/          # equals() contract
│       ├── chapter04/lab18/          # Composition over inheritance
│       ├── chapter05/lab31/          # PECS wildcards
│       ├── chapter11/lab78/          # Synchronization
│       └── [more chapters...]
│
├── tests-validation/                 # 🧪 Validation tests
│   └── src/test/java/
│
├── benchmarks/                       # ⏱️ Performance experiments
│   └── src/main/java/
│
└── docs/                             # 📚 Lab guides
    ├── LAB_INDEX.md                  # Master index of all labs
    ├── LEARNING_PATH.md              # Recommended study order
    └── chapter02/, chapter03/, ...   # Detailed lab guides
```

---

## 🚀 Getting Started

### 1. Build the Project

```bash
cd HandsOnEffectiveJava
mvn clean compile
```

### 2. Start Your First Lab

1. **Read the guide:** `docs/chapter02/LAB01_StaticFactories.md`
2. **Open the file:** `labs/src/main/java/effectivejava/labs/chapter02/lab01/DatabaseConnection.java`
3. **Follow the TODOs** in the code
4. **Run the demo:** Execute `ConnectionClient.main()`
5. **Verify improvement**

### 3. Workflow for Each Lab

```
📖 Read lab guide in docs/
    ↓
📝 Open lab file(s)
    ↓
🔧 Fix the code following TODO/FIXME markers
    ↓
▶️ Run main() or tests to validate
    ↓
💭 Reflect on trade-offs
```

---

## 📋 Labs Overview

Each lab file contains:
- **Header block** explaining the scenario and problem
- **TODO markers** showing exactly what to fix
- **main() method** for quick validation
- **Reflection prompts** for deeper understanding

### Example Lab Structure

```java
/**
 * ============================================================
 * LAB 01: Static Factory Methods vs Constructors (Item 1)
 * ============================================================
 * 
 * SCENARIO: [realistic context]
 * 
 * PROBLEMS TO FIX: [what's wrong]
 * 
 * YOUR TASK:
 * TODO #1: [specific task]
 * TODO #2: [specific task]
 * 
 * VALIDATION: [how to verify your fix]
 * ============================================================
 */
public class DatabaseConnection {
    
    // FIXME: Telescoping constructors!
    public DatabaseConnection(...) { ... }
    
    // TODO: Add your static factory methods here
    
}
```

---

## 📚 Chapter Coverage

| Chapter | Topic | Key Labs |
|---------|-------|----------|
| 2 | Creating/Destroying Objects | Static factories, Builders, Singletons, DI |
| 3 | Methods Common to All Objects | equals, hashCode, toString, Comparable |
| 4 | Classes and Interfaces | Immutability, Composition, Interfaces |
| 5 | Generics | Raw types, PECS, Wildcards |
| 6 | Enums and Annotations | Type-safe enums, EnumSet/Map |
| 7 | Lambdas and Streams | When to use streams |
| 8 | Methods | Validation, defensive copies, Optional |
| 9 | General Programming | Scope, precision, naming |
| 10 | Exceptions | Checked vs unchecked |
| 11 | Concurrency | Synchronization, visibility, atomicity |
| 12 | Serialization | Alternatives, security |

---

## 🎓 Learning Goals

By completing this project, you will:

1. **Internalize** Effective Java principles through muscle memory
2. **Recognize** anti-patterns and code smells instantly
3. **Reason** about API design trade-offs
4. **Apply** these principles naturally in production code

---

## 📋 Quick Start Commands

```bash
# Build everything
mvn clean compile

# Run a specific lab's main method
mvn exec:java -pl labs -Dexec.mainClass="effectivejava.labs.chapter02.lab01.ConnectionClient"

# Run tests
mvn test -pl tests-validation

# Run benchmarks
cd benchmarks && mvn package && java -jar target/benchmarks.jar
```

---

*Happy Refactoring!* 🚀
