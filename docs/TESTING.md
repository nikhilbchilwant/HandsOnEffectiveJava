# Testing Guide

This document explains how to run and write tests for validating your 
Effective Java refactoring exercises.

---

## 🔧 Running Tests

### Run All Tests

```bash
mvn test -pl tests-validation
```

### Run Tests for a Specific Chapter

```bash
mvn test -pl tests-validation -Dtest="Chapter02*"
mvn test -pl tests-validation -Dtest="Chapter03*"
```

### Run Tests for a Specific Lab

```bash
mvn test -pl tests-validation -Dtest="Chapter02Lab01Test"
mvn test -pl tests-validation -Dtest="Chapter03Lab10Test"
```

### Run "Before" or "After" Tests Only

```bash
# Run only the "before" tests (should pass, demonstrate problems)
mvn test -pl tests-validation -Dtest="*BeforeTest"

# Run only the "after" tests (your implementation)
mvn test -pl tests-validation -Dtest="*AfterTest"
```

---

## 📝 Test Structure

Each lab has two categories of tests:

### Before Tests (Demonstrate the Problem)

These tests:
- Pass with the flawed implementation
- Demonstrate what's wrong
- Help you understand the issue before fixing

### After Tests (Validate Your Fix)

These tests:
- Initially fail (your implementation doesn't exist)
- Pass after you implement the refactored version
- Validate correctness and contract compliance

---

## ✍️ Writing Your Own Tests

Tests use JUnit 5 and AssertJ:

```java
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class MyRefactoredClassTest {
    
    @Test
    void shouldDoSomething() {
        // Given
        MyClass sut = new MyClass();
        
        // When
        var result = sut.doSomething();
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getValue()).isEqualTo(42);
    }
    
    @Test
    void shouldThrowForInvalidInput() {
        MyClass sut = new MyClass();
        
        assertThatThrownBy(() -> sut.doSomething(null))
            .isInstanceOf(NullPointerException.class);
    }
}
```

---

## 🎯 Test Naming Conventions

We use descriptive test names:

```java
void contractViolation_symmetryBroken()      // Before: shows the flaw
void contract_symmetryMaintained()           // After: validates the fix

void performance_cachedInstanceReturned()    // Validates caching
void threadSafety_noRaceConditions()         // Validates concurrency
```

---

## 🔍 Testing Concurrency

For concurrency labs (Chapter 11), use these patterns:

```java
@Test
@RepeatedTest(100)  // Run many times to catch race conditions
void shouldBeThreadSafe() throws Exception {
    var executor = Executors.newFixedThreadPool(10);
    var latch = new CountDownLatch(10);
    var sut = new MyThreadSafeClass();
    
    for (int i = 0; i < 10; i++) {
        executor.submit(() -> {
            sut.concurrentOperation();
            latch.countDown();
        });
    }
    
    latch.await(5, TimeUnit.SECONDS);
    assertThat(sut.getCount()).isEqualTo(10);
}
```

---

## ⚙️ Test Dependencies

The tests-validation module has access to:

- **JUnit 5**: Test framework
- **AssertJ**: Fluent assertions
- **Mockito**: Mocking framework

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
</dependency>
<dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-core</artifactId>
</dependency>
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
</dependency>
```

---

## 📊 Code Coverage (Optional)

To run tests with coverage:

```bash
mvn verify -pl tests-validation
```

Coverage reports will be in `target/site/jacoco/`.

---

## 💡 Tips

1. **Read tests before implementing**: They define the expected behavior

2. **Run before tests first**: Understand what's broken

3. **Add edge case tests**: The provided tests may not cover everything

4. **Test failure messages matter**: Use descriptive assertion messages

5. **Keep tests independent**: Each test should work in isolation
