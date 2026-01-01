# Effective Java Labs - Complete Item Index

All **90 items** from "Effective Java" (3rd Edition, Joshua Bloch) mapped to labs.
Items marked with ✅ have code ready in `labs/`. Fix the code by following TODO markers.

**Current Coverage: 84/90 items (93%)**

---

## Chapter 2: Creating and Destroying Objects (pp. 5-35)

| Lab | Item | Title | Status |
|-----|------|-------|--------|
| LAB01 | Item 1 | Consider static factory methods instead of constructors | ✅ |
| LAB02 | Item 2 | Consider a builder when faced with many constructor parameters | ✅ |
| LAB03 | Item 3 | Enforce the singleton property with a private constructor or enum | ✅ |
| LAB04 | Item 4 | Enforce noninstantiability with a private constructor | ✅ |
| LAB05 | Item 5 | Prefer dependency injection to hardwiring resources | ✅ |
| LAB06 | Item 6 | Avoid creating unnecessary objects | ✅ |
| LAB07 | Item 7 | Eliminate obsolete object references | ✅ |
| LAB08 | Item 8 | Avoid finalizers and cleaners | ✅ |
| LAB09 | Item 9 | Prefer try-with-resources to try-finally | ✅ |

**Chapter 2: 9/9 Complete** ✅

---

## Chapter 3: Methods Common to All Objects (pp. 37-72)

| Lab | Item | Title | Status |
|-----|------|-------|--------|
| LAB10 | Item 10 | Obey the general contract when overriding equals | ✅ |
| LAB11 | Item 11 | Always override hashCode when you override equals | ✅ |
| LAB12 | Item 12 | Always override toString | ✅ |
| LAB13 | Item 13 | Override clone judiciously | ✅ |
| LAB14 | Item 14 | Consider implementing Comparable | ✅ |

**Chapter 3: 5/5 Complete** ✅

---

## Chapter 4: Classes and Interfaces (pp. 73-116)

| Lab | Item | Title | Status |
|-----|------|-------|--------|
| LAB15 | Item 15 | Minimize the accessibility of classes and members | ✅ |
| LAB16 | Item 16 | In public classes, use accessor methods, not public fields | ✅ |
| LAB17 | Item 17 | Minimize mutability | ✅ |
| LAB18 | Item 18 | Favor composition over inheritance | ✅ |
| LAB19 | Item 19 | Design and document for inheritance or else prohibit it | ✅ |
| LAB20 | Item 20 | Prefer interfaces to abstract classes | ✅ |
| LAB21 | Item 21 | Design interfaces for posterity | ✅ |
| LAB22 | Item 22 | Use interfaces only to define types | ✅ |
| LAB23 | Item 23 | Prefer class hierarchies to tagged classes | ✅ |
| LAB24 | Item 24 | Favor static member classes over nonstatic | ✅ |
| LAB25 | Item 25 | Limit source files to a single top-level class | ✅ |

**Chapter 4: 11/11 Complete** ✅

---

## Chapter 5: Generics (pp. 117-156)

| Lab | Item | Title | Status |
|-----|------|-------|--------|
| LAB26 | Item 26 | Don't use raw types | ✅ |
| LAB27 | Item 27 | Eliminate unchecked warnings | ✅ |
| LAB28 | Item 28 | Prefer lists to arrays | ✅ |
| LAB29 | Item 29 | Favor generic types | ✅ |
| LAB30 | Item 30 | Favor generic methods | ✅ |
| LAB31 | Item 31 | Use bounded wildcards to increase API flexibility | ✅ |
| LAB32 | Item 32 | Combine generics and varargs judiciously | ✅ |
| LAB33 | Item 33 | Consider typesafe heterogeneous containers | ✅ |

**Chapter 5: 8/8 Complete** ✅

---

## Chapter 6: Enums and Annotations (pp. 157-192)

| Lab | Item | Title | Status |
|-----|------|-------|--------|
| LAB34 | Item 34 | Use enums instead of int constants | ✅ |
| LAB35 | Item 35 | Use instance fields instead of ordinals | ✅ |
| LAB36 | Item 36 | Use EnumSet instead of bit fields | ✅ |
| LAB37 | Item 37 | Use EnumMap instead of ordinal indexing | ✅ |
| LAB38 | Item 38 | Emulate extensible enums with interfaces | ✅ |
| LAB39 | Item 39 | Prefer annotations to naming patterns | ✅ |
| LAB40 | Item 40 | Consistently use the Override annotation | ✅ |
| LAB41 | Item 41 | Use marker interfaces to define types | ✅ |

**Chapter 6: 8/8 Complete** ✅

---

## Chapter 7: Lambdas and Streams (pp. 193-226)

| Lab | Item | Title | Status |
|-----|------|-------|--------|
| LAB42 | Item 42 | Prefer lambdas to anonymous classes | ✅ |
| LAB43 | Item 43 | Prefer method references to lambdas | ✅ |
| LAB44 | Item 44 | Favor the use of standard functional interfaces | ✅ |
| LAB45 | Item 45 | Use streams judiciously | ✅ |
| LAB46 | Item 46 | Prefer side-effect-free functions in streams | ✅ |
| LAB47 | Item 47 | Prefer Collection to Stream as a return type | ✅ |
| LAB48 | Item 48 | Use caution when making streams parallel | ✅ |

**Chapter 7: 7/7 Complete** ✅

---

## Chapter 8: Methods (pp. 227-260)

| Lab | Item | Title | Status |
|-----|------|-------|--------|
| LAB49 | Item 49 | Check parameters for validity | ✅ |
| LAB50 | Item 50 | Make defensive copies when needed | ✅ |
| LAB51 | Item 51 | Design method signatures carefully | ✅ |
| LAB52 | Item 52 | Use overloading judiciously | ✅ |
| LAB53 | Item 53 | Use varargs judiciously | ✅ |
| LAB54 | Item 54 | Return empty collections or arrays, not nulls | ✅ |
| LAB55 | Item 55 | Return optionals judiciously | ✅ |
| LAB56 | Item 56 | Write doc comments for all exposed API elements | 📋 |

**Chapter 8: 7/8 Complete**

---

## Chapter 9: General Programming (pp. 261-292)

| Lab | Item | Title | Status |
|-----|------|-------|--------|
| LAB57 | Item 57 | Minimize the scope of local variables | ✅ |
| LAB58 | Item 58 | Prefer for-each loops to traditional for loops | ✅ |
| LAB59 | Item 59 | Know and use the libraries | ✅ |
| LAB60 | Item 60 | Avoid float and double if exact answers are required | ✅ |
| LAB61 | Item 61 | Prefer primitive types to boxed primitives | ✅ |
| LAB62 | Item 62 | Avoid strings where other types are more appropriate | 📋 |
| LAB63 | Item 63 | Beware the performance of string concatenation | ✅ |
| LAB64 | Item 64 | Refer to objects by their interfaces | ✅ |
| LAB65 | Item 65 | Prefer interfaces to reflection | 📋 |
| LAB66 | Item 66 | Use native methods judiciously | 📋 |
| LAB67 | Item 67 | Optimize judiciously | 📋 |
| LAB68 | Item 68 | Adhere to generally accepted naming conventions | 📋 |

**Chapter 9: 7/12 Complete**

---

## Chapter 10: Exceptions (pp. 293-310)

| Lab | Item | Title | Status |
|-----|------|-------|--------|
| LAB69 | Item 69 | Use exceptions only for exceptional conditions | ✅ |
| LAB70 | Item 70 | Use checked exceptions for recoverable/runtime for errors | ✅ |
| LAB71 | Item 71 | Avoid unnecessary use of checked exceptions | ✅ |
| LAB72 | Item 72 | Favor the use of standard exceptions | ✅ |
| LAB73 | Item 73 | Throw exceptions appropriate to the abstraction | ✅ |
| LAB74 | Item 74 | Document all exceptions thrown by each method | ✅ |
| LAB75 | Item 75 | Include failure-capture information in detail messages | ✅ |
| LAB76 | Item 76 | Strive for failure atomicity | ✅ |
| LAB77 | Item 77 | Don't ignore exceptions | ✅ |

**Chapter 10: 9/9 Complete** ✅

---

## Chapter 11: Concurrency (pp. 311-338)

| Lab | Item | Title | Status |
|-----|------|-------|--------|
| LAB78 | Item 78 | Synchronize access to shared mutable data | ✅ |
| LAB79 | Item 79 | Avoid excessive synchronization | ✅ |
| LAB80 | Item 80 | Prefer executors, tasks, and streams to threads | ✅ |
| LAB81 | Item 81 | Prefer concurrency utilities to wait and notify | ✅ |
| LAB82 | Item 82 | Document thread safety | ✅ |
| LAB83 | Item 83 | Use lazy initialization judiciously | ✅ |
| LAB84 | Item 84 | Don't depend on the thread scheduler | ✅ |

**Chapter 11: 7/7 Complete** ✅

---

## Chapter 12: Serialization (pp. 339-367)

| Lab | Item | Title | Status |
|-----|------|-------|--------|
| LAB85 | Item 85 | Prefer alternatives to Java serialization | ✅ |
| LAB86 | Item 86 | Implement Serializable with great caution | ✅ |
| LAB87 | Item 87 | Consider using a custom serialized form | ✅ |
| LAB88 | Item 88 | Write readObject methods defensively | ✅ |
| LAB89 | Item 89 | For instance control, prefer enum types to readResolve | ✅ |
| LAB90 | Item 90 | Consider serialization proxies instead of serialized | ✅ |

**Chapter 12: 6/6 Complete** ✅

---

## Summary

| Chapter | Topic | Complete | Status |
|---------|-------|----------|--------|
| 2 | Creating/Destroying Objects | 9/9 | ✅ 100% |
| 3 | Methods Common to All | 5/5 | ✅ 100% |
| 4 | Classes and Interfaces | 11/11 | ✅ 100% |
| 5 | Generics | 8/8 | ✅ 100% |
| 6 | Enums and Annotations | 8/8 | ✅ 100% |
| 7 | Lambdas and Streams | 7/7 | ✅ 100% |
| 8 | Methods | 7/8 | 88% |
| 9 | General Programming | 7/12 | 58% |
| 10 | Exceptions | 9/9 | ✅ 100% |
| 11 | Concurrency | 7/7 | ✅ 100% |
| 12 | Serialization | 6/6 | ✅ 100% |
| **Total** | | **84/90** | **93%** |

---

## Getting Started

All labs are in `labs/src/main/java/effectivejava/labs/chapterXX/labYY/`

Each file has:
- **Header** explaining the problem and scenario
- **FIXME** markers showing bugs to fix
- **TODO** markers showing what to implement  
- **main()** method for quick testing

### Quick Start
```bash
# Compile all labs
mvn compile -pl labs

# Run a specific lab
cd labs
mvn exec:java -Dexec.mainClass="effectivejava.labs.chapter02.lab01.DatabaseConnection"
```

**Start with:** `chapter02/lab01/DatabaseConnection.java`

---

## Recommended Learning Path

### Week 1-2: Foundations (Items 1-14)
- LAB01-LAB09 (Chapter 2: Creating Objects) ✅
- LAB10-LAB14 (Chapter 3: Common Object Methods) ✅

### Week 3-4: Classes & Generics (Items 15-33)
- LAB15-LAB25 (Chapter 4: Classes/Interfaces) ✅
- LAB26-LAB33 (Chapter 5: Generics) ✅

### Week 5-6: Enums & Lambdas (Items 34-48)
- LAB34-LAB41 (Chapter 6: Enums/Annotations) ✅  
- LAB42-LAB48 (Chapter 7: Lambdas/Streams) ✅

### Week 7-8: Methods & Exceptions (Items 49-77)
- LAB49-LAB55 (Chapter 8: Methods)
- LAB57-LAB64 (Chapter 9)
- LAB69-LAB77 (Chapter 10: Exceptions) ✅

### Week 9-10: Concurrency & Serialization (Items 78-90)
- LAB78-LAB84 (Chapter 11: Concurrency) ✅
- LAB85-LAB90 (Chapter 12: Serialization) ✅
