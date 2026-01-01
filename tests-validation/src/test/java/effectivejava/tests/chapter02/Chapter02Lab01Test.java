package effectivejava.tests.chapter02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for LAB01: Static Factory Methods vs Constructors
 * 
 * These tests validate your refactoring work. Run them before and after
 * your changes to see the improvement.
 */
public class Chapter02Lab01Test {

    // ========================================
    // BEFORE TESTS - Run against flawed code
    // These should PASS but demonstrate problems
    // ========================================
    
    @Nested
    @DisplayName("Before Refactoring (Demonstrates Problems)")
    class BeforeTests {
        
        @Test
        @DisplayName("Telescoping constructors are confusing")
        void constructorConfusion_multipleCombinations() {
            // Import: effectivejava.flawed.chapter02.lab01.DatabaseConnection
            
            // TODO: Uncomment when running tests
            // This test demonstrates the problem - parameters are meaningless
            
            // DatabaseConnection conn = new DatabaseConnection(
            //     "localhost", 5432, "mydb", "user", "pass",
            //     true,   // What is this?
            //     false,  // And this?
            //     30000,  // Timeout? Retries? Milliseconds?
            //     60000,  // ??
            //     true,   // Read-only?
            //     10,     // Pool size?
            //     false   // Lazy?
            // );
            
            // Test passes but code is hard to understand!
            assertThat(true).isTrue();
        }
        
        @Test
        @DisplayName("No instance caching - creates new objects always")
        void noInstanceCaching_createsNewObjectsAlways() {
            // TODO: Import flawed DatabaseConnection
            
            // DatabaseConnection conn1 = new DatabaseConnection("localhost", "test");
            // DatabaseConnection conn2 = new DatabaseConnection("localhost", "test");
            
            // These are identical configurations but different objects!
            // assertThat(conn1).isNotSameAs(conn2);
            
            // For some use cases (like dev connections), we could reuse!
            assertThat(true).isTrue();
        }
    }
    
    // ========================================
    // AFTER TESTS - Run against refactored code
    // These should PASS after your refactoring
    // ========================================
    
    @Nested
    @DisplayName("After Refactoring (Validates Improvements)")
    class AfterTests {
        
        @Test
        @DisplayName("Factory method names are self-documenting")
        void factoryMethodNaming_isSelfDocumenting() {
            // TODO: Import YOUR refactored DatabaseConnection
            
            // Clear intent from method name:
            // DatabaseConnection local = DatabaseConnection.localDevConnection();
            // DatabaseConnection prod = DatabaseConnection.productionConnection("host", 5432);
            // DatabaseConnection pooled = DatabaseConnection.pooledConnection(10, true);
            
            // Much clearer than new DatabaseConnection(true, false, 5000, ...)!
            assertThat(true).isTrue();
        }
        
        @Test
        @DisplayName("Instance caching reuses identical connections")
        void instanceCaching_reusesIdenticalConnections() {
            // TODO: Import your refactored version
            
            // DatabaseConnection local1 = DatabaseConnection.localDevConnection();
            // DatabaseConnection local2 = DatabaseConnection.localDevConnection();
            
            // Same instance returned (cached)
            // assertThat(local1).isSameAs(local2);
            
            assertThat(true).isTrue();
        }
        
        @Test
        @DisplayName("Can return subtype through static factory")
        void returnTypeFlexibility_canReturnSubtypes() {
            // If you created a Connection interface:
            // Connection conn = DatabaseConnection.localDevConnection();
            // This allows returning different implementation later without changing API
            
            assertThat(true).isTrue();
        }
        
        @Test
        @DisplayName("Constructors are private")
        void constructorVisibility_isPrivate() throws Exception {
            // TODO: Verify constructor is private via reflection
            
            // Constructor<?>[] constructors = 
            //     YourDatabaseConnection.class.getDeclaredConstructors();
            // for (Constructor<?> c : constructors) {
            //     assertThat(Modifier.isPrivate(c.getModifiers()))
            //         .as("Constructor should be private")
            //         .isTrue();
            // }
            
            assertThat(true).isTrue();
        }
    }
}
